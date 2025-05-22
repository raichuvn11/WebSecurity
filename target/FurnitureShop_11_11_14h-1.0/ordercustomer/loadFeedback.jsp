<%--
  Created by IntelliJ IDEA.
  User: ANH THU
  Date: 11/22/2024
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String cspNonce = (String) request.getAttribute("cspNonce"); %>

<script nonce="<%= cspNonce %>">
    function viewFeedbackCustomer(orderID) {
        loadFeedback(orderID);
    }
    function loadFeedback(orderID) {
        $.ajax({
            url: "/admin-customer-order/" + orderID,
            type: "POST",
            dataType: "json",
            success: function(response) {
                if (response.id && response.id !== null) {
                    $('#feedback-description').text(response.description || 'Không có phản hồi.');

                    // Hiển thị số sao đánh giá
                    let rateStars = '';
                    for (let i = 0; i < response.rate; i++) {
                        rateStars += '<span class="color-gold">&#9733;</span>';
                    }
                    for (let i = response.rate; i < 5; i++) {
                        rateStars += '<span class="color-lightgray">&#9733;</span>';
                    }

                    $('#feedback-rate').html('Rate: ' + rateStars);

                    $('#feedback-image').html('Rate: ' + rateStars);

                    if (response.imageFeedbacks && response.imageFeedbacks.length > 0) {
                        var imageHtml = '';
                        response.imageFeedbacks.forEach(function (imageBase64, index) {
                            imageHtml += '<a class="image-feedback-a">' +
                                '<img src="data:image/jpeg;base64,' + imageBase64 + '" ' +
                                'alt="Feedback Image ' + (index + 1) + '" ' +
                                'class="img-feedback"/>' +
                                '</a>';
                        });
                        $('#imageFeedback').html(imageHtml).show();

                        // Gắn sự kiện click sau khi các ảnh được thêm vào DOM
                        $('.image-feedback-a').on('click', function() {
                            const imageBase64 = $(this).find('img').attr('src').split(',')[1]; // Lấy phần base64 từ src
                            showLargeImage(imageBase64);
                        });
                    } else {
                        $('#imageFeedback').html('<p>Không có ảnh phản hồi.</p>').show();
                    }

                    $('#feedback').modal('show');
                } else {
                    $('#feedbackNull').modal('show');
                }
            },
            error: function () {
                console.log("error");
                $('#feedbackNull').modal('show');
            }
        });
    }

    function showLargeImage(imageBase64) {
        $('#largeImage').attr('src', 'data:image/jpeg;base64,' + imageBase64);
        $('#imageModal').modal('show');
    }
</script>