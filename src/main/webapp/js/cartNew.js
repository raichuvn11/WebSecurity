document.addEventListener('DOMContentLoaded', function () {
    let selectedFurnitureId = null;

    // ===== XỬ LÝ MUA HÀNG =====
    const checkoutBtn = document.getElementById('checkoutBtn');
    const purchaseForm = document.getElementById('purchaseForm');

    if (checkoutBtn && purchaseForm) {
        checkoutBtn.addEventListener('click', function (e) {
            const selectedProducts = [];
            const checkboxes = document.getElementsByName('selectedProducts');

            for (let i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked) {
                    selectedProducts.push(checkboxes[i].value);
                }
            }

            if (selectedProducts.length === 0) {
                alert("Vui lòng chọn ít nhất một sản phẩm để mua.");
                e.preventDefault();
                return;
            }

            const hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.name = 'listFurnitureID';
            hiddenInput.value = selectedProducts.join(',');
            purchaseForm.appendChild(hiddenInput);
        });
    }

    // ===== XỬ LÝ MỞ MODAL XÁC NHẬN XÓA =====
    const modalElements = document.querySelectorAll('.btn-show-modal');
    modalElements.forEach(button => {
        button.addEventListener('click', function () {
            selectedFurnitureId = this.dataset.id;
            const modal = document.getElementById(`confirmCloseModal-${selectedFurnitureId}`);
            if (modal) {
                modal.style.display = 'block';
            }
        });
    });

    // ===== XỬ LÝ ĐÓNG MODAL =====
    const cancelButtons = document.querySelectorAll('.btn-confirm-no');
    cancelButtons.forEach(button => {
        button.addEventListener('click', function () {
            document.querySelectorAll('.modal').forEach(modal => {
                modal.style.display = 'none';
            });
            selectedFurnitureId = null;
        });
    });

    // ===== XỬ LÝ GỬI FORM XÓA SẢN PHẨM =====
    const confirmButtons = document.querySelectorAll('.btn-confirm-yes');
    confirmButtons.forEach(button => {
        button.addEventListener('click', function () {
            const furnitureId = this.dataset.id;

            const form = document.createElement('form');
            form.action = "../PurchaseServlet";
            form.method = "POST";

            const actionInput = document.createElement('input');
            actionInput.type = "hidden";
            actionInput.name = "action";
            actionInput.value = "removefromcart";
            form.appendChild(actionInput);

            const idInput = document.createElement('input');
            idInput.type = "hidden";
            idInput.name = "furnitureID";
            idInput.value = furnitureId;
            form.appendChild(idInput);

            document.body.appendChild(form);
            form.submit();
        });
    });
});
