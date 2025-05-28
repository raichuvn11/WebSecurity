<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String cspNonce = (String) request.getAttribute("cspNonce"); %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Untree.co">
    <link rel="shortcut icon" href="favicon.png">

    <meta name="description" content="" />
    <meta name="keywords" content="bootstrap, bootstrap4" />

    <!-- Bootstrap CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/all.min.css" rel="stylesheet">
    <link href="../css/tiny-slider.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet">
    <link href="../css/feedback.css" rel="stylesheet">

    <script src="../js/feedback.js"></script>

    <title>Furni Free Bootstrap 5 Template for Furniture and Interior Design Websites by Untree.co </title>
</head>

<body>

<c:import url="../includes/header.jsp" />
<%Long orderId = Long.parseLong(request.getParameter("orderId"));%>
<%Long customerId = Long.parseLong(request.getParameter("customerId"));%>

<!-- Start Hero Section -->
<div class="hero">
    <div class="container">
        <div class="row justify-content-between">
            <div class="col-lg-5">
                <div class="intro-excerpt">
                    <h1>Đánh giá đơn hàng</h1>
                </div>
            </div>
            <div class="col-lg-7">

            </div>
        </div>
    </div>
</div>
<!-- End Hero Section -->

<div class="feedback-container">
    <h2>Phản hồi của bạn</h2>
    <div class="emojis-rate">
        <img src="../images/rate1.png" alt="Very Dissatisfied" class="emoji-rate" data-rate="1">
        <img src="../images/rate2.png" alt="Dissatisfied" class="emoji-rate" data-rate="2">
        <img src="../images/rate3.png" alt="Neutral" class="emoji-rate" data-rate="3">
        <img src="../images/rate4.png" alt="Satisfied" class="emoji-rate" data-rate="4">
        <img src="../images/rate5.png" alt="Very Satisfied" class="emoji-rate" data-rate="5">
    </div>

    <textarea id="textFeedback" class="text-feedback" placeholder="Nhập phản hồi của bạn ở đây..."></textarea>

    <div class="image-upload-container">
        <h2>Chọn ảnh về đơn hàng</h2>
        <input type="file" id="imageInput" accept="image/*" multiple>
        <div id="previewContainer"></div>
    </div>

    <!-- Gắn data-* vào nút -->
    <button type="button" class="btn-feedback"
            data-customer-id="<%= customerId %>"
            data-order-id="<%= orderId %>">Gửi</button>
</div>

<c:import url="../includes/footer.jsp" />

<script src="../js/bootstrap.bundle.min.js"></script>
<script src="../js/tiny-slider.js"></script>
<script src="../js/custom.js"></script>
<script nonce="<%= cspNonce %>">
    document.addEventListener('DOMContentLoaded', function () {
        // Biến lưu rating được chọn
        let selectedRate = 0;

        // Bắt sự kiện click vào emoji
        document.querySelectorAll('.emoji-rate').forEach(function (emoji) {
            emoji.addEventListener('click', function () {
                selectedRate = parseInt(emoji.getAttribute('data-rate'));
                selectIcon(emoji, selectedRate);
            });
        });

        // Bắt sự kiện click nút gửi phản hồi
        const feedbackButton = document.querySelector('.btn-feedback');
        if (feedbackButton) {
            feedbackButton.addEventListener('click', function () {
                const customerId = feedbackButton.getAttribute('data-customer-id');
                const orderId = feedbackButton.getAttribute('data-order-id');
                feedbackOrder(customerId, orderId, selectedRate);
            });
        }
    });
</script>
</body>

</html>
