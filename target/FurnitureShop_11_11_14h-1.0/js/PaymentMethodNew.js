// --- Xử lý hiển thị thông tin theo phương thức thanh toán ---
document.querySelectorAll('input[name="paymentMethod"]').forEach(elem => {
    elem.addEventListener("change", function () {
        document.getElementById("collapsebank").classList.remove("show");
        document.getElementById("collapsecash").classList.remove("show");

        if (this.id === "paymentBank") {
            document.getElementById("collapsebank").classList.add("show");
        } else if (this.id === "paymentCash") {
            document.getElementById("collapsecash").classList.add("show");
        }
    });
});

// --- Cập nhật tổng tiền theo số lượng ---
function updateTotal() {
    let grandTotal = 0;

    document.querySelectorAll('.quantity').forEach(input => {
        let quantity = parseInt(input.value);
        if (isNaN(quantity) || quantity < 1) {
            quantity = 1;
            input.value = quantity;
        }
        const price = parseInt(input.getAttribute('data-price'));
        const totalCell = document.getElementById(input.getAttribute('data-total'));
        const totalPrice = price * quantity;

        totalCell.textContent = Math.round(totalPrice).toFixed(0);
        grandTotal += totalPrice;
    });

    document.getElementById('grand-total').textContent = Math.round(grandTotal).toFixed(0);
    updateCouponDetails();
}

// --- Cập nhật tổng tiền sau khi áp dụng mã giảm giá ---
function updateAmount() {
    const order = parseFloat(document.getElementById('grand-total').textContent);
    const couponCode = document.getElementById('c_code').value;

    const formData = new URLSearchParams();
    formData.append('couponCode', couponCode);
    formData.append('action', 'coupon');
    formData.append('total', order);

    getFurnitureQuantities().forEach(furniture => {
        formData.append('listCategoryID', furniture.furnitureID);
        formData.append('quantity', furniture.quantity);
    });

    fetch('PurchaseServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData.toString()
    })
        .then(res => res.text())
        .then(data => {
            let discount = 0;
            if (data === 'FullUse') {
                alert('Mã giảm giá đã hết lượt sử dụng');
            } else if (data !== 'NoCoupon') {
                discount = parseFloat(data);
            }

            const totalAmount = order - discount;
            document.getElementById('discount').textContent = Math.round(discount).toFixed(0);
            document.getElementById('amount').textContent = Math.round(totalAmount).toFixed(0);
        })
        .catch(err => console.error('Lỗi khi gọi servlet:', err));
}

document.getElementById('c_code').addEventListener('change', updateCouponDetails);

// --- Cập nhật thông tin chi tiết mã giảm giá ---
function updateCouponDetails() {
    const cCode = document.getElementById('c_code');
    const selectedOption = cCode.options[cCode.selectedIndex];

    if (selectedOption.value !== "") {
        const couponType = selectedOption.getAttribute('data-coupon-type');
        const couponValue = parseInt(selectedOption.getAttribute('data-coupon-value'));
        const endDate = selectedOption.getAttribute('data-end-date');
        const conditions = selectedOption.getAttribute('data-conditions');
        const minOrderValue = parseInt(selectedOption.getAttribute('data-min-order-value'));
        const useLimit = parseInt(selectedOption.getAttribute('data-use-limit'));
        const currentUsage = parseInt(selectedOption.getAttribute('data-current-usage'));

        document.getElementById('data-end-date').textContent = endDate;
        document.getElementById('data-conditions').textContent = (conditions === 'all') ? 'Tất cả sản phẩm' :
            (conditions === 'min') ? `Giá trị đơn hàng tối thiểu: ${minOrderValue}` :
                'Các sản phẩm nhất định';
        document.getElementById('data-coupon-value').textContent = (couponType === "money") ? couponValue : couponValue + "%";
        document.getElementById('data-current-usage').textContent = useLimit - currentUsage;
        document.getElementById('coupon-details').style.display = 'block';
    } else {
        document.getElementById('coupon-details').style.display = 'none';
    }
    updateAmount();
}

// --- Lấy danh sách sản phẩm và số lượng ---
function getFurnitureQuantities() {
    const furnitureQuantities = [];
    document.querySelectorAll('td[data-id]').forEach(td => {
        const furnitureID = td.getAttribute('data-id');
        const quantity = parseInt(td.querySelector('.quantity').value);
        if (furnitureID && quantity > 0) {
            furnitureQuantities.push({ furnitureID, quantity });
        }
    });
    return furnitureQuantities;
}

// --- Xử lý khi người dùng nhấn thanh toán ---
function checkMethodPayment() {
    const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
    const amount = parseFloat(document.getElementById('amount').textContent);
    const couponCode = document.getElementById('c_code').value;

    const formData = new URLSearchParams();
    formData.append('couponCode', couponCode);
    formData.append('paymentMethod', paymentMethod);

    getFurnitureQuantities().forEach(furniture => {
        formData.append('listCategoryID', furniture.furnitureID);
        formData.append('quantity', furniture.quantity);
    });
    formData.append('amount', amount);

    if (paymentMethod === "bank") {
        formData.append('action', 'QRCODE');

        let description = document.getElementById('description').value;
        if (description.length > 15) {
            description = description.substring(0, description.length - 15).trim();
        }

        const now = new Date();
        const dateTimeNow = `${now.getFullYear()}${String(now.getMonth() + 1).padStart(2, '0')}${String(now.getDate()).padStart(2, '0')} ` +
            `${String(now.getHours()).padStart(2, '0')}${String(now.getMinutes()).padStart(2, '0')}${String(now.getSeconds()).padStart(2, '0')}`;

        description += " " + dateTimeNow;
        document.getElementById('description').value = description;

        // Hiển thị overlay chờ
        document.getElementById('overlay').style.display = 'flex';

        formData.append('description', description);

        fetch('PurchaseServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: formData.toString()
        })
            .then(res => res.text())
            .then(qrCodeUrl => {
                document.getElementById('overlay').style.display = 'none';
                if (qrCodeUrl !== 'False') {
                    showPaymentModal(qrCodeUrl, amount, description, couponCode);
                } else {
                    alert('Sản phẩm không đủ hoặc đã ngưng kinh doanh!');
                }
            })
            .catch(err => {
                document.getElementById('overlay').style.display = 'none';
                console.error('Lỗi khi gọi QR code:', err);
            });
    } else {
        formData.append('action', 'payment');
        fetch('PurchaseServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: formData.toString()
        })
            .then(res => res.text())
            .then(data => {
                if (data === 'True') {
                    alert('Đặt hàng thành công!');
                    window.location.href = "shopServlet";
                } else if (data === 'False') {
                    alert('Đặt hàng thất bại, sản phẩm không đủ hoặc đã ngưng kinh doanh!');
                } else {
                    alert('Mã giảm giá đã hết lượt sử dụng!');
                    location.reload();
                }
            })
            .catch(err => console.error('Lỗi khi thanh toán:', err));
    }
}

let checkPaidInterval = null;
let countdown;
let countdownTime = 600; // 10 phút
let isSuccess = false;
let stop = true;

// --- Hiển thị modal mã QR và bắt đầu đếm ngược ---
function showPaymentModal(qrCodeUrl, amount, description, couponCode) {
    const modal = document.getElementById('paymentModal');
    const qrCodeImage = document.getElementById('qr-code-img');

    qrCodeImage.src = qrCodeUrl;
    modal.style.display = 'block';
    document.body.classList.add('modal-open');

    clearInterval(countdown);
    countdownTime = 600;
    updateTimerDisplay();
    startCountdown();

    setTimeout(() => {
        checkPaidInterval = setInterval(() => {
            checkPaid(amount, description, couponCode);
        }, 1000);
    }, 20000);
}

// --- Cập nhật hiển thị thời gian đếm ngược ---
function updateTimerDisplay() {
    const timerDisplay = document.getElementById("timer");
    let minutes = Math.floor(countdownTime / 60);
    let seconds = countdownTime % 60;
    timerDisplay.textContent = `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
}

// --- Bắt đầu đếm ngược thời gian ---
function startCountdown() {
    const timerDisplay = document.getElementById("timer");

    countdown = setInterval(() => {
        updateTimerDisplay();

        if (countdownTime <= 0) {
            clearInterval(countdown);
            closeModal();
        }
        countdownTime--;
    }, 1000);
}

// --- Đóng modal khi nhấn nút đóng ---
function closeModal() {
    document.getElementById("confirmCloseModal").style.display = "block";
    document.body.classList.remove('modal-open');
}

// --- Xử lý khi người dùng đồng ý đóng modal ---
document.getElementById("confirmCloseYes").onclick = function () {
    document.getElementById("paymentModal").style.display = "none";
    document.getElementById("confirmCloseModal").style.display = "none";
    clearInterval(checkPaidInterval);
    clearInterval(countdown);
    isSuccess = false;
    stop = true;
};

// --- Hủy đóng modal ---
document.getElementById("confirmCloseNo").onclick = function () {
    document.getElementById("confirmCloseModal").style.display = "none";
    document.body.classList.add('modal-open');
};

// --- Kiểm tra trạng thái thanh toán chuyển khoản qua Google Script ---
async function checkPaid(price, content, couponCode) {
    if (isSuccess) {
        if (stop) {
            const amount = parseFloat(document.getElementById('amount').textContent);
            const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;

            const formData = new URLSearchParams();
            formData.append('couponCode', couponCode);
            formData.append('paymentMethod', paymentMethod);
            formData.append('amount', amount);
            formData.append('action', 'payment');

            getFurnitureQuantities().forEach(furniture => {
                formData.append('listCategoryID', furniture.furnitureID);
                formData.append('quantity', furniture.quantity);
            });

            stop = false;

            fetch('PurchaseServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData.toString()
            })
                .then(res => res.text())
                .then(data => {
                    if (data === 'True') {
                        alert('Đặt hàng thành công!');
                        window.location.href = "shopServlet";
                    } else if (data === 'False') {
                        alert('Đặt hàng thất bại, sản phẩm không đủ hoặc đã ngưng kinh doanh!');
                    } else {
                        alert('Mã giảm giá đã hết lượt sử dụng!');
                        location.reload();
                    }
                })
                .catch(err => console.error('Lỗi khi xác nhận thanh toán:', err));
        }
    } else {
        try {
            const response = await fetch("https://script.google.com/macros/s/AKfycbyXH6MugTcWjRNvC5rH_ynvCvq44B-VWdKwCleA5fEYKemBisrgSCdBXR_Z6QccAybM/exec");
            const data = await response.json();
            const lastPaid = data.data[data.data.length - 1];
            const lastPrice = lastPaid["Giá trị"];
            const lastContent = lastPaid["Mô tả"];

            if (lastPrice >= price && lastContent.includes(content)) {
                isSuccess = true;
            }
        } catch (error) {
            console.error("Lỗi khi kiểm tra thanh toán:", error);
        }
    }
}
