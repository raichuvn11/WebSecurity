document.addEventListener('DOMContentLoaded', function () {
    const logoutLink = document.getElementById('logoutLink');
    if (logoutLink) {
        logoutLink.addEventListener('click', function (e) {
            e.preventDefault();
            if (confirm("Bạn có chắc chắn muốn đăng xuất?")) {
                window.location.href = "../LogoutServlet";
            }
        });
    }
});