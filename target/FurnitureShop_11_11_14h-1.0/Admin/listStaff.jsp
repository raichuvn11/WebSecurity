<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String cspNonce = (String) request.getAttribute("cspNonce"); %>

<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script nonce="<%= cspNonce %>">
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Quản lý nhân viên";
        const listStaffElement = document.getElementById("list-staff");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>
<%------------------------------------------------%>
<div class="page-wrapper">
    <div class="content">

        <div class="page-header">
            <div class="page-title">
                <h4>Quản lý nhân viên</h4>
                <h6>Thêm/Chỉnh sửa nhân viên</h6>
            </div>
            <div class="page-btn">
                <a href="${pageContext.request.contextPath}/addStaff" class="btn btn-added"><img src="assets/img/icons/plus.svg" alt="img">Thêm nhân viên</a>
            </div>

        </div>
        <style nonce="<%= cspNonce %>">
            .margin-right5{
                margin-right: 5px;
            }
            .wh50{
                width: 50px; height: 50px;
            }
            .span-green{
                color: green; font-style: italic
            }
            .span-red{
                color: red; font-style: italic
            }
            .wh24{
                width: 24px; height: 24px;
            }
        </style>
        <div class="card">
            <div class="card-body">
                <div class="table-top">
                    <div class="search-set form-group">
                        <div class="search-path">
                            <a class="btn btn-filter" id="filter_search">
                                <img src="assets/img/icons/filter.svg" alt="img">
                                <span><img src="assets/img/icons/closes.svg" alt="img"></span>
                            </a>
                        </div>
                        <form action="${pageContext.request.contextPath}/searchStaff" method="post" class="d-flex justify-content-center">
                            <input type="text" class="form-control margin-right5" name="search-name" value="${searchName}" placeholder="Nhập tên tìm kiếm...">
                            <button type="submit" name="search-action" value="search-name" class="btn btn-filters ms-auto">
                                <img src="assets/img/icons/search-whites.svg" alt="img">
                            </button>
                        </form>
                    </div>
                </div>

                <div class="card" id="filter_inputs">
                    <div class="card-body pb-0">
                        <form action="${pageContext.request.contextPath}/searchStaff" method="post">
                            <div class="row">
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <select name="search-status" id="search-status" class="form-control select mb-3">
                                        <option value="all">Trạng thái...</option>
                                        <option value="Active">Đang làm việc</option>
                                        <option value="InActive">Đã nghỉ việc</option>
                                    </select>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <select name="search-salary" id="search-salary" class="form-control select mb-3">
                                        <option value="all">Lương...</option>
                                        <option value="under10">Dưới 10 triệu</option>
                                        <option value="10to20">Từ 10 đến 20 triệu</option>
                                        <option value="over20">Trên 20 triệu</option>
                                    </select>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <select name="search-age" id="search-age" class="form-control select mb-3">
                                        <option value="all">Tuổi...</option>
                                        <option value="18to25">Từ 18 - 25 tuổi</option>
                                        <option value="25to30">Từ 25 - 30 tuổi</option>
                                        <option value="over30">Trên 30 tuổi</option>
                                    </select>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <select name="search-workTime" id="search-worktime" class="form-control select mb-3">
                                        <option value="all">Thời gian làm việc...</option>
                                        <option value="under1">Dưới 1 năm</option>
                                        <option value="1to3">Từ 1 - 3 năm</option>
                                        <option value="3to5">Từ 3 - 5 năm</option>
                                        <option value="over5">Trên 5 năm</option>
                                    </select>
                                </div>
                                <div class="col-lg-1 col-sm-6 col-12 ms-auto">
                                    <div class="form-group">
                                        <button type="submit" name="search-action" value="search-filters" class="btn btn-filters ms-auto">
                                            <img src="assets/img/icons/search-whites.svg" alt="img">
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Nhân viên</th>
                            <th>Số điện thoại</th>
                            <th>Email</th>
                            <th>Lương (VND)</th>
                            <th>Ngày làm việc</th>
                            <th>Trạng thái</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody id="employee-table">
                        <%@ page import="data.ImageUtil" %>
                        <c:forEach var="staff" items="${listStaff}">
                            <tr>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <c:if test="${staff.avatar != null}">
                                            <img src="data:image/jpeg;base64,${ImageUtil.DisplayImage(staff.avatar)}" alt="Avatar" class="rounded-circle me-2 wh50" >
                                        </c:if>
                                        <c:if test="${staff.avatar == null}">
                                            <img src="images/blankavatar.jpg" alt="Avatar" class="rounded-circle me-2 wh50">
                                        </c:if>
                                        <span>${staff.name}</span>
                                    </div>
                                </td>
                                <td>${staff.phone}</td>
                                <td>${staff.email}</td>
                                <td><fmt:formatNumber value="${staff.salary}" pattern="0.##" /></td>
                                <td><fmt:formatDate value="${staff.workDate}" pattern="yyyy-MM-dd" /></td>
                                <td>
                                    <c:if test="${staff.status == 'Active'}">
                                        <span class="span-green">Đang làm việc</span>
                                    </c:if>
                                    <c:if test="${staff.status == 'InActive'}">
                                        <span class="span-red">Đã nghỉ việc</span>
                                    </c:if>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/editStaff" method="post" class="d-inline me-3" title="Chi tiết">
                                        <input type="hidden" name="emp-id" value="${staff.personID}"/>
                                        <button type="submit" class="btn p-0 border-0 bg-transparent">
                                            <img src="assets/img/icons/edit.svg" alt="Edit" class="wh24">
                                        </button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/deleteStaff" method="post" id="delete-form" class="d-inline me-3" title="Xóa">
                                        <input type="hidden" name="emp-id" value="${staff.personID}"/>
                                        <c:if test="${staff.status == 'Active'}">
                                            <button type="submit" name="action" value="delete" class="btn p-0 border-0 bg-transparent" id="delete-button">
                                                <img src="assets/img/icons/delete.svg" alt="Delete" class="wh24">
                                            </button>

                                        </c:if>
                                        <c:if test="${staff.status == 'InActive'}">
                                            <button type="submit" name="action" value="restore" class="btn p-0 border-0 bg-transparent" id="restore-button">
                                                <img src="assets/img/icons/restore.svg" alt="Restore" class="wh24">
                                            </button>
                                        </c:if>
                                    </form>

                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="container mt-5">
                    <div class="d-flex justify-content-center pagination form-group">
                        <button id="prev-page" class="btn btn-primary me-2" disabled>&lt;</button>
                        <span id="page-info" class="align-self-center">Page 1 of X </span>
                        <button id="next-page" class="btn btn-primary ms-2">&gt;</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<script src="scripts/pagination.js"></script>
<script nonce="<%= cspNonce %>">
    function confirmSubmission(messageConfirm) {
        return confirm(messageConfirm);
    }
    document.addEventListener('DOMContentLoaded', function() {
        const deleteBtn = document.getElementById('delete-button');
        if (deleteBtn) {
            deleteBtn.addEventListener('click', function(event) {
                if (!confirmSubmission('Xác nhận xóa nhân viên này?')) {
                    event.preventDefault(); // ngăn submit form nếu không xác nhận
                }
            });
        }
    });
    document.addEventListener('DOMContentLoaded', function() {
        const restoreBtn = document.getElementById('restore-button');
        if (restoreBtn) {
            restoreBtn.addEventListener('click', function(event) {
                if (!confirmSubmission('Xác nhận khôi phục tài khoản nhân viên này?')) {
                    event.preventDefault(); // Ngăn submit nếu user cancel
                }
            });
        }
    });
</script>
<c:import url="footer.jsp"/>