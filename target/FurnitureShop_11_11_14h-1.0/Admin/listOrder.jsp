<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% String cspNonce = (String) request.getAttribute("cspNonce"); %>

<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script nonce="<%= cspNonce %>">
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Danh sách đơn hàng";
        const listStaffElement = document.getElementById("list-category");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>

<div class="page-wrapper">
    <div class="content">
        <style nonce="<%= cspNonce %>">
            .page-header{
                display: flex; justify-content: space-between; align-items: flex-start; padding: 20px; border-bottom: 1px solid #ddd;
            }
            .customer-profile{display: flex; gap: 20px; align-items: stretch; height: auto;}
            .img{
                flex-shrink: 0; height: 200px; width: 180px; display: flex; align-items: stretch;
            }

            .avatar{
                height: 100%; width: 100%; object-fit: cover; display: block;
            }
            .tittle{
                text-align: right; margin-top: 20px;
            }
            .h4{
                font-weight: bold; font-size: 1.8em; margin: 0; font-family: 'Poppins', sans-serif; color: #333;
            }
            .h6{
                font-size: 1.1em; color: #777; margin: 5px 0 0; font-family: 'Roboto', sans-serif;
            }
            .search{
                text-align: left; margin-bottom: 15px; padding: 10px; border-left: 5px solid #28a745; border-radius: 5px;
            }
            .search-title{
                font-size: 1.2em; font-weight: 400; color: #4a4a4a; font-family: 'Poppins', sans-serif; font-style: italic; margin: 0;
                padding: 8px 12px; display: flex; align-items: center; background-color: #f9fbfd; border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); text-shadow: 0px 1px 1px rgba(0, 0, 0, 0.1); letter-spacing: 0.5px;
            }
            .margin-left{
                margin-left: 10px;
            }
            .margin-bottom{
                margin-bottom: 15px;
            }
            .margin-bottom0{
                margin-bottom: 0;
            }
            .margin-top{
                margin-top: 10px;
            }
            .category{
                width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #ccc;
            }
            .td{
                max-width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;
            }
            .w90{
                max-width: 90%; width: 90%;
            }
            .large-image{
                width: 100%; max-height: 600px;
            }
            .form{
                width: 100%;
                padding: 10px;

                border: 1px solid #ccc;
                background-color: #f9f9f9;
                font-size: 14px;
                color: #333;
                transition: all 0.3s ease;
            }
            .color{
                color: #888;
            }
            .o1{
                background-color: #d4edda; color: #155724;
            }
            .o2{
                background-color: #fff3cd; color: #856404;
            }
            .o3{
                background-color: #f8d7da; color: #721c24;
            }
            .o4{
                background-color: #cce5ff; color: #004085;
            }
            .o5{
                background-color: #d1ecf1; color: #0c5460;
            }
            .o6{
                background-color: #e2e3e5; color: #6c757d;
            }
            .o7{
                background-color: #d4edda; color: #155724;
            }
            .category-name{
                white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 150px;
            }
            .img-feedback-a{
                display: inline-block; margin: 5px; cursor: pointer;
            }
            .img-feedback{
                max-width: 100px; max-height: 100px; border: 1px solid #ccc;
            }
            .color-gold{
                color: gold;
            }
            .color-lightgray{
                color: lightgray;
            }
        </style>
        <div class="page-header">
            <!-- Phần thông tin khách hàng -->
            <div class="customer-profile">
                <!-- Ảnh đại diện -->
                <div class="product-img img" >
                    <c:choose>
                        <c:when test="${not empty customer.avatar}">
                            <img src="data:image/jpeg;base64,${customer.avatar}" alt="Avatar" class="avatar" />
                        </c:when>
                        <c:otherwise>
                            <img src="https://cellphones.com.vn/sforum/wp-content/uploads/2023/10/avatar-trang-4.jpg" alt="Avatar" class="avatar" />
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Thông tin khách hàng -->
                <div id="customer-info">
                    <div>
                        <span>Họ và tên:</span>
                        <span>${customer.name}</span>
                    </div>
                    <div>
                        <span>Số điện thoại:</span>
                        <span>${customer.phone}</span>
                    </div>
                    <div>
                        <span>Email:</span>
                        <span>${customer.email}</span>
                    </div>
                    <div>
                        <span>Địa chỉ:</span>
                        <span>${customer.address}</span>
                    </div>
                    <div>
                        <span>Trạng thái:</span>
                        <span>${customer.status}</span>
                    </div>
                </div>
            </div>

            <!-- Phần Quản lý đơn hàng -->
            <div class="page-title tittle">
                <h4 class="h4">Quản Lý Đơn Hàng</h4>
                <h6 class="h6">Tìm kiếm/xem phản hồi/xem chi tiết đơn hàng</h6>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/admin-customer-order" method="get"
              id="listForm">
            <div class="card">

                <div class="card-body">
                    <div class="search-header search">
                        <h6 class="search-title ">
                            🔍 <span class="margin-left">Tìm Kiếm Đơn Hàng</span>
                        </h6>

                    </div>
                    <div class="card-body pb-0">
                        <div class="row">
                            <input type="hidden" name="customerId" value="${searchOrder.customerId}" placeholder="Nhập mã khách hàng...">
                            <div class="col-lg-2 col-sm-6 col-12 margin-bottom">
                                <div class="form-group margin-bottom0" >
                                    <input type="number" name="id" value="${searchOrder.id}" placeholder="Nhập mã sản phẩm..." class="category">
                                </div>
                            </div>

                            <div class="col-lg-2 col-sm-6 col-12 margin-bottom">
                                <div class="form-group margin-bottom0" >
                                    <input type="date" name="orderDate" value="${searchOrder.orderDate}" class="form-control category" >
                                </div>
                            </div>

                            <div class="col-lg-2 col-sm-6 col-12 margin-bottom">
                                <div class="form-group margin-bottom0">
                                    <select name="status" class="form-control form">
                                        <option value="" class="color">Chọn trạng thái</option>
                                        <option value="DELIVERING" ${searchOrder.status == 'Đang giao hàng' ? 'selected' : ''}  class="o1">Đang giao hàng</option>
                                        <option value="WAITING_PROCESS" ${searchOrder.status == 'Đang chờ xử lý' ? 'selected' : ''} class="o2">Đang chờ xử lý</option>
                                        <option value="CANCELED" ${searchOrder.status == 'Đã hủy' ? 'selected' : ''} class="o3">Đã hủy</option>
                                        <option value="DELIVERED" ${searchOrder.status == 'Đã giao hàng' ? 'selected' : ''} class="o4">Đã giao hàng</option>
                                        <option value="ACCEPTED" ${searchOrder.status == 'Đã chấp nhận' ? 'selected' : ''} class="o5">Đã chấp nhận</option>
                                        <option value="REFUNDED" ${searchOrder.status == 'Đã hoàn trả' ? 'selected' : ''} class="o6">Đã hoàn trả</option>
                                        <option value="FEEDBACKED" ${searchOrder.status == 'Đã nhận phản hồi' ? 'selected' : ''} class="o7">Đã nhận phản hồi</option>
                                    </select>
                                </div>
                            </div>

                            <div
                                    class="col-lg-2 col-sm-6 col-12 ms-auto">
                                <button type="button"
                                        class="btn btn-success"
                                        id="btnSearchOrder">
                                    <svg xmlns="http://www.w3.org/2000/svg"
                                         width="16"
                                         height="16"
                                         fill="currentColor"
                                         class="bi bi-search"
                                         viewBox="0 0 16 16">
                                        <path
                                                d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0">
                                        </path>
                                    </svg>
                                    Tìm Kiếm
                                </button>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="table-responsive">
                        <table class="table" id="orderList">

                            <thead>
                            <tr>
                                <th>
                                    <label class="checkboxs">
                                        <input type="checkbox" id="select-all">
                                        <span class="checkmarks"></span>
                                    </label>
                                </th>
                                <th>Mã Đơn Hàng</th>
                                <th>Ngày Đặt Hàng</th>
                                <th>Trạng Thái</th>
                                <th>Xem Chi Tiết</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <td>
                                        <label class="checkboxs">
                                            <input type="checkbox">
                                            <span class="checkmarks"></span>
                                        </label>
                                    </td>
                                    <td>${order.id}</td>
                                    <td>
                                        <fmt:formatDate value="${order.orderDate}" pattern="MM/dd/yyyy" />
                                    </td>
                                    <td>
                                            <span class="badges
                                                <c:choose>
                                                    <c:when test="${order.status == 'Đang giao hàng'}">bg-lightgreen</c:when>
                                                    <c:when test="${order.status == 'Đang chờ xử lý'}">bg-warning</c:when>
                                                    <c:when test="${order.status == 'Đã hủy'}">bg-danger</c:when>
                                                    <c:when test="${order.status == 'Đã giao hàng'}">bg-primary</c:when>
                                                    <c:when test="${order.status == 'Đã chấp nhận'}">bg-info</c:when>
                                                    <c:when test="${order.status == 'Đã hoàn trả'}">bg-secondary</c:when>
                                                    <c:when test="${order.status == 'Đã nhận phản hồi'}">bg-success</c:when>
                                                    <c:otherwise>bg-secondary</c:otherwise>
                                                </c:choose>
                                            ">
                                                    ${order.status}
                                            </span>
                                    </td>
                                    <td>
                                        <!-- Xem phản hồi -->
                                        <a  href="javascript:void(0);"
                                            class="me-3 btn-view-feedback"
                                            data-order-id="${order.id}"
                                            title="Xem Phản Hồi">
                                        <img src="${pageContext.request.contextPath}/assets/img/icons/edit.svg" alt="Edit">
                                        </a>

                                        <!-- Xem chi tiết sản phẩm -->
                                        <a  href="javascript:void(0);"
                                            class="me-3 btn-view-product"
                                            data-order-id="${order.id}"
                                            title="Xem Chi Tiết">
                                            <img src="${pageContext.request.contextPath}/assets/img/icons/product.svg" alt="Product">
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- Phân trang -->
                    <div class="container mt-5">
                        <div class="d-flex justify-content-center pagination form-group">
                            <button id="prev-page" type="button" class="btn btn-primary me-2" disabled>&lt;</button>
                            <span id="page-info" class="align-self-center">Page 1 of X</span>
                            <button id="next-page" type="button" class="btn btn-primary ms-2">&gt;</button>
                        </div>
                    </div>
                </div>

            </div>
        </form>
    </div>
</div>
<div class="modal fade" id="feedback">
    <div class="modal-dialog">
        <div class="modal-content" id="feedbackCustomer">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Phản Hồi Của Khách Hàng</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="closeFeedbackBtn">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p id="feedback-description"></p>
                <div id="feedback-rate">
                </div>
                <div id="imageFeedback" class="margin-top">
                    <!-- Danh sách ảnh sẽ được thêm vào đây bằng JavaScript -->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" id="cancelFeedbackBtn">Hủy Thao Tác</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="productOfOrderList" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-dialog modal-xl w90" role="document" >
        <div class="modal-content" id="orderModalContent">
            <div class="modal-header" id="orderModalHeader">
                <h5 class="modal-title" id="modalTitle">Chi Tiết Hóa Đơn</h5>
                <button type="button" class="close btn-close-product-order-list" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="orderModalBody">
                <!-- Thông tin thanh toán -->
                <div id="paymentInfo">
                    <h5>Thông Tin Thanh Toán</h5>
                    <table>
                        <tr>
                            <td class="payment-label">Tổng chi phí:</td>
                            <td class="payment-value" id="paymentTotalPrice">13,500,000 VNĐ</td>
                        </tr>
                        <tr>
                            <td class="payment-label">Mã giảm giá:</td>
                            <td class="payment-value" id="paymentCoupon">Giảm giá 5%</td>
                        </tr>
                        <tr>
                            <td class="payment-label">Phương thức thanh toán:</td>
                            <td class="payment-value" id="paymentMethod">Cash on Delivery</td>
                        </tr>
                        <tr>
                            <td class="payment-label">Tiền thanh toán:</td>
                            <td class="payment-value" id="paymentMoney">2,000,000 VNĐ</td>
                        </tr>
                    </table>
                </div>
                <!-- Danh sách sản phẩm -->
                <div id="productList">
                    <h5>Danh Sách Sản Phẩm</h5>
                    <div class="table-responsive1">
                        <table class="table table-bordered1">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Nội Thất</th>
                                <th>Mô Tả</th>
                                <th>Giá Một Sản Phẩm</th>
                                <th>Trạng Thái</th>
                                <th>Số Lượng</th>
                                <th>Tổng Chi Phí</th>
                            </tr>
                            </thead>
                            <tbody id="productOrderTableBody">
                            <!-- Dữ liệu sẽ được điền động từ JavaScript -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer" id="orderModalFooter">
                <button type="button" class="btn btn-secondary btn-close-product-order-list" data-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal for showing large image -->
<!-- Modal for showing large image -->
<div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="imageModalLabel"></h5>
                <button type="button" class="close btn-close-image-modal" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Ảnh lớn sẽ hiển thị ở đây -->
                <img id="largeImage" src="" alt="Large Feedback Image" class="large-image"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="feedbackNull" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="thu">Phản hồi từ khách hàng</h5>
                <button type="button" class="close btn-close-feedback-null" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times; </span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Chưa có phản hồi từ khách hàng.</h6>
                <br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger btn-close-feedback-null">Đóng</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="orderNull" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="thu1">Chi Tiết Hóa Đơn </h5>
                <button type="button" class="close close-modal-btn" data-target="#orderNull" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times; </span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Khách Hàng Chưa Thanh Toán</h6>
                <br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger close-modal-btn" data-target="#orderNull">Đóng</button>
            </div>
        </div>
    </div>
</div>
<%--<script src="scripts/pagination.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/js/jquery.slimscroll.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/js/jquery.dataTables.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/dataTables.bootstrap4.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/plugins/select2/js/select2.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalerts.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.4.0/jspdf.umd.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>--%>

<c:import url="footer.jsp"/>
<script src="${pageContext.request.contextPath}/ordercustomer/pageorderCustomer.js"></script>
<script src="${pageContext.request.contextPath}/ordercustomer/loadAndSearchOrder.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/ordercustomer/customer.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/ordercustomer/modelDetailBill.css">

<jsp:include page="${pageContext.request.contextPath}/ordercustomer/loadFeedback.jsp"></jsp:include>
<jsp:include page="${pageContext.request.contextPath}/ordercustomer/loadProductOfOrder.jsp"></jsp:include>

<script nonce="<%= cspNonce %>">
    document.addEventListener('DOMContentLoaded', function () {
        /* Xem phản hồi */
        document.querySelectorAll('.btn-view-feedback').forEach(function (link) {
            link.addEventListener('click', function () {
                const orderId = link.getAttribute('data-order-id');
                viewFeedbackCustomer(orderId);
            });
        });

        /* Xem chi tiết sản phẩm */
        document.querySelectorAll('.btn-view-product').forEach(function (link) {
            link.addEventListener('click', function () {
                const orderId = link.getAttribute('data-order-id');
                viewListProduct(orderId);
            });
        });
    });
    document.addEventListener('DOMContentLoaded', function () {
        const closeFeedbackBtn = document.getElementById('closeFeedbackBtn');
        if (closeFeedbackBtn) {
            closeFeedbackBtn.addEventListener('click', function () {
                $('#feedback').modal('hide');
            });
        }
    });
    document.addEventListener('DOMContentLoaded', function () {
        const cancelBtn = document.getElementById('cancelFeedbackBtn');
        if (cancelBtn) {
            cancelBtn.addEventListener('click', function () {
                $('#feedback').modal('hide');
            });
        }
    });
    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('.btn-close-product-order-list').forEach(function(btn) {
            btn.addEventListener('click', function() {
                $('#productOfOrderList').modal('hide');
            });
        });
    });

    document.addEventListener('DOMContentLoaded', function () {
        document.querySelectorAll('.btn-close-image-modal').forEach(function (button) {
            button.addEventListener('click', function () {
                $('#imageModal').modal('hide');
            });
        });
    });
    document.addEventListener('DOMContentLoaded', function () {
        document.querySelectorAll('.btn-close-feedback-null').forEach(function (button) {
            button.addEventListener('click', function () {
                $('#feedbackNull').modal('hide');
            });
        });
    });
    document.addEventListener('DOMContentLoaded', function () {
        document.querySelectorAll('.close-modal-btn').forEach(function (btn) {
            btn.addEventListener('click', function () {
                const modalId = btn.getAttribute('data-target');
                if (modalId) {
                    $(modalId).modal('hide');
                }
            });
        });
    });
</script>