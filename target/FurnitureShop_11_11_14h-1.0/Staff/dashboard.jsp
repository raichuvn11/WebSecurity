<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="business.Staff" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
  <meta name="description" content="POS - Bootstrap Admin Template">
  <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, management, minimal, modern,  html5, responsive">
  <meta name="author" content="Dreamguys - Bootstrap Admin Template">
  <meta name="robots" content="noindex, nofollow">
  <title>Our Staff</title>

  <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/img/favicon.jpg">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dataTables.bootstrap4.min.css">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/fontawesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/all.min.css">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
  <script src="../scripts/logout.js"></script>
</head>
<body>
<div id="global-loader">
  <div class="whirly-loader"> </div>
</div>

<div class="main-wrapper">

  <div class="header">

    <div class="header-left active">

      <a href="${pageContext.request.contextPath}/Staff/dashboard.jsp" class="logo">
        <img src="${pageContext.request.contextPath}/images/logoshop2.png" alt="" style="height: 100px;">
      </a>
      <a href="${pageContext.request.contextPath}/Staff/dashboard.jsp" class="logo-small">
        <img src="${pageContext.request.contextPath}/images/logoFurni.png" alt="">
      </a>
      <a id="toggle_btn" href="javascript:void(0);">
      </a>
    </div>

    <a id="mobile_btn" class="mobile_btn" href="#sidebar">
<span class="bar-icon">
<span></span>
<span></span>
<span></span>
</span>
    </a>

    <ul class="nav user-menu">

      <li class="nav-item">
        <div class="top-nav-search">
          <a href="javascript:void(0);" class="responsive-search">
            <i class="fa fa-search"></i>
          </a>
          <form action="#">
            <div class="searchinputs">
              <input type="text" placeholder="Search Here ...">
              <div class="search-addon">
                <span><img src="${pageContext.request.contextPath}/assets/img/icons/closes.svg" alt="img"></span>
              </div>
            </div>
            <a class="btn" id="searchdiv"><img src="${pageContext.request.contextPath}/assets/img/icons/search.svg" alt="img"></a>
          </form>
        </div>
      </li>


      <li class="nav-item dropdown has-arrow flag-nav">
        <div class="dropdown-menu dropdown-menu-right">
          <a href="javascript:void(0);" class="dropdown-item">
            <img src="${pageContext.request.contextPath}/assets/img/flags/us.png" alt="" height="16"> English
          </a>
          <a href="javascript:void(0);" class="dropdown-item">
            <img src="${pageContext.request.contextPath}/assets/img/flags/fr.png" alt="" height="16"> French
          </a>
          <a href="javascript:void(0);" class="dropdown-item">
            <img src="${pageContext.request.contextPath}/assets/img/flags/es.png" alt="" height="16"> Spanish
          </a>
          <a href="javascript:void(0);" class="dropdown-item">
            <img src="${pageContext.request.contextPath}/assets/img/flags/de.png" alt="" height="16"> German
          </a>
        </div>
      </li>


      <li class="nav-item dropdown">
        <a href="javascript:void(0);" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
          <img src="${pageContext.request.contextPath}/assets/img/icons/notification-bing.svg" alt="img"> <span class="badge rounded-pill">4</span>
        </a>
        <div class="dropdown-menu notifications">
          <div class="topnav-dropdown-header">
            <span class="notification-title">Notifications</span>
            <a href="javascript:void(0)" class="clear-noti"> Clear All </a>
          </div>
          <div class="noti-content">
            <ul class="notification-list">
              <li class="notification-message">
                <a href="activities.html">
                  <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="assets/img/profiles/avatar-02.jpg">
</span>
                    <div class="media-body flex-grow-1">
                      <p class="noti-details"><span class="noti-title">John Doe</span> added new task <span class="noti-title">Patient appointment booking</span></p>
                      <p class="noti-time"><span class="notification-time">4 mins ago</span></p>
                    </div>
                  </div>
                </a>
              </li>
              <li class="notification-message">
                <a href="activities.html">
                  <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="assets/img/profiles/avatar-03.jpg">
</span>
                    <div class="media-body flex-grow-1">
                      <p class="noti-details"><span class="noti-title">Tarah Shropshire</span> changed the task name <span class="noti-title">Appointment booking with payment gateway</span></p>
                      <p class="noti-time"><span class="notification-time">6 mins ago</span></p>
                    </div>
                  </div>
                </a>
              </li>
              <li class="notification-message">
                <a href="activities.html">
                  <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="${pageContext.request.contextPath}/assets/img/profiles/avatar-06.jpg">
</span>
                    <div class="media-body flex-grow-1">
                      <p class="noti-details"><span class="noti-title">Misty Tison</span> added <span class="noti-title">Domenic Houston</span> and <span class="noti-title">Claire Mapes</span> to project <span class="noti-title">Doctor available module</span></p>
                      <p class="noti-time"><span class="notification-time">8 mins ago</span></p>
                    </div>
                  </div>
                </a>
              </li>
              <li class="notification-message">
                <a href="activities.html">
                  <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="${pageContext.request.contextPath}/assets/img/profiles/avatar-17.jpg">
</span>
                    <div class="media-body flex-grow-1">
                      <p class="noti-details"><span class="noti-title">Rolland Webber</span> completed task <span class="noti-title">Patient and Doctor video conferencing</span></p>
                      <p class="noti-time"><span class="notification-time">12 mins ago</span></p>
                    </div>
                  </div>
                </a>
              </li>
              <li class="notification-message">
                <a href="activities.html">
                  <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="${pageContext.request.contextPath}/assets/img/profiles/avatar-13.jpg">
</span>
                    <div class="media-body flex-grow-1">
                      <p class="noti-details"><span class="noti-title">Bernardo Galaviz</span> added new task <span class="noti-title">Private chat module</span></p>
                      <p class="noti-time"><span class="notification-time">2 days ago</span></p>
                    </div>
                  </div>
                </a>
              </li>
            </ul>
          </div>
          <div class="topnav-dropdown-footer">
            <a href="activities.html">View all Notifications</a>
          </div>
        </div>
      </li>

      <li class="nav-item dropdown has-arrow main-drop">
        <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
<span class="user-img"><img src="${pageContext.request.contextPath}/assets/img/profiles/avator1.jpg" alt="">
<span class="status online"></span></span>
        </a>
        <div class="dropdown-menu menu-drop-user">
          <div class="profilename">
            <div class="profileset">
<span class="user-img"><img src="${pageContext.request.contextPath}/assets/img/profiles/avator1.jpg" alt="">
<span class="status online"></span></span>
              <div class="profilesets">
                <h6>John Doe</h6>
                <h5>Admin</h5>
              </div>
            </div>
            <hr class="m-0">
            <a class="dropdown-item" href="../loadProfile"> <i class="me-2" data-feather="user"></i> My Profile</a>
            <a class="dropdown-item" href="generalsettings.html"><i class="me-2" data-feather="settings"></i>Settings</a>
            <hr class="m-0">
            <a class="dropdown-item logout pb-0" onclick ="confirmLogout()"><img src="assets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
          </div>
        </div>
      </li>
    </ul>


    <div class="dropdown mobile-user-menu">
      <a href="javascript:void(0);" class="nav-link dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
      <div class="dropdown-menu dropdown-menu-right">
        <a class="dropdown-item" href="profile.html">My Profile</a>
        <a class="dropdown-item" href="generalsettings.html">Settings</a>
        <a class="dropdown-item" href="signin.html">Logout</a>
      </div>
    </div>

  </div>


  <div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
      <div id="sidebar-menu" class="sidebar-menu">
        <ul>
          <li class="active">
            <a href="${pageContext.request.contextPath}/Staff/dashboard.jsp"><img src="${pageContext.request.contextPath}/assets/img/icons/dashboard.svg" alt="img"><span> Trang chủ</span> </a>
          </li>
          <li class="submenu">


          <li class="submenu">
            <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/assets/img/icons/calendars.svg" alt="img"><span> Công việc </span> <span class="menu-arrow"></span></a>
            <ul>











              <li><a href="${pageContext.request.contextPath}/Staff/quotationList">Xử lý đơn</a></li>
              <li><a href="${pageContext.request.contextPath}/Staff/loadCustomerList">Tư vấn khách hàng</a></li>
              <%--<li><a href="${pageContext.request.contextPath}/Staff/loadStaffChatList">Chat (KH)</a></li>--%>
















            </ul>
          </li>
          <li class="submenu">


        </ul>
      </div>
    </div>
  </div>

  <div class="page-wrapper">
    <div class="content">
      <div class="row">
        <div class="col-lg-3 col-sm-6 col-12">
          <div class="dash-widget">
            <div class="dash-widgetimg">
              <span><img src="${pageContext.request.contextPath}/assets/img/icons/dash1.svg" alt="img"></span>
            </div>
            <div class="dash-widgetcontent">
              <h5><span class="counters" data-count="307144.00">1</span></h5>
              <h6>Tin nhắn đang chờ</h6>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6 col-12">
          <div class="dash-widget dash1">
            <div class="dash-widgetimg">
              <span><img src="${pageContext.request.contextPath}/assets/img/icons/dash2.svg" alt="img"></span>
            </div>
            <div class="dash-widgetcontent">
              <h5><span class="counters" data-count="4385.00">100</span></h5>
              <h6>Tin nhắn</h6>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6 col-12">
          <div class="dash-widget dash2">
            <div class="dash-widgetimg">
              <span><img src="${pageContext.request.contextPath}/assets/img/icons/dash3.svg" alt="img"></span>
            </div>
            <div class="dash-widgetcontent">
              <h5><span class="counters" data-count="385656.50">385,656.50</span></h5>
              <h6>Delegated</h6>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6 col-12">
          <div class="dash-widget dash3">
            <div class="dash-widgetimg">
              <span><img src="${pageContext.request.contextPath}/assets/img/icons/dash4.svg" alt="img"></span>
            </div>
            <div class="dash-widgetcontent">
              <h5><span class="counters" data-count="40000.00">400.00</span></h5>
              <h6>Delegated</h6>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6 col-12 d-flex">
          <div class="dash-count">
            <div class="dash-counts">
              <h4>02</h4>
              <h5>Đơn đang chờ</h5>
            </div>
            <div class="dash-imgs">
              <i data-feather="user"></i>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6 col-12 d-flex">
          <div class="dash-count das1">
            <div class="dash-counts">
              <h4>100</h4>
              <h5>Đơn đã xử lý</h5>
            </div>
            <div class="dash-imgs">
              <i data-feather="user-check"></i>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6 col-12 d-flex">
          <div class="dash-count das2">
            <div class="dash-counts">
              <h4>100</h4>
              <h5>Đơn đang xử lý</h5>
            </div>
            <div class="dash-imgs">
              <i data-feather="file-text"></i>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6 col-12 d-flex">
          <div class="dash-count das3">
            <div class="dash-counts">
              <h4>105</h4>
              <h5>Delegated</h5>
            </div>
            <div class="dash-imgs">
              <i data-feather="file"></i>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-7 col-sm-12 col-12 d-flex">
          <div class="card flex-fill">
            <div class="card-header pb-0 d-flex justify-content-between align-items-center">
              <h5 class="card-title mb-0">Purchase & Sales</h5>
              <div class="graph-sets">
                <ul>
                  <li>
                    <span>Sales</span>
                  </li>
                  <li>
                    <span>Purchase</span>
                  </li>
                </ul>
                <div class="dropdown">
                  <button class="btn btn-white btn-sm dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                    2022 <img src="${pageContext.request.contextPath}/assets/img/icons/dropdown.svg" alt="img" class="ms-2">
                  </button>
                  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <li>
                      <a href="javascript:void(0);" class="dropdown-item">2022</a>
                    </li>
                    <li>
                      <a href="javascript:void(0);" class="dropdown-item">2021</a>
                    </li>
                    <li>
                      <a href="javascript:void(0);" class="dropdown-item">2020</a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="card-body">
              <div id="sales_charts"></div>
            </div>
          </div>
        </div>
        <div class="col-lg-5 col-sm-12 col-12 d-flex">
          <div class="card flex-fill">
            <div class="card-header pb-0 d-flex justify-content-between align-items-center">
              <h4 class="card-title mb-0">Các đơn hàng gần đây</h4>
              <div class="dropdown">
                <a href="javascript:void(0);" data-bs-toggle="dropdown" aria-expanded="false" class="dropset">
                  <i class="fa fa-ellipsis-v"></i>
                </a>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                  <li>
                    <a href="productlist.jsp" class="dropdown-item">Product List</a>
                  </li>
                  <li>
                    <a href="addproduct.jsp" class="dropdown-item">Product Add</a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="card-body">
              <div class="table-responsive dataview">
                <table class="table datatable ">
                  <thead>
                  <tr>
                    <th>Sno</th>
                    <th>Products</th>
                    <th>Price</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td>1</td>
                    <td class="productimgname">
                      <a href="productlist.jsp" class="product-img">
                        <img src="${pageContext.request.contextPath}/assets/img/product/product22.jpg" alt="product">
                      </a>
                      <a href="productlist.jsp">Apple Earpods</a>
                    </td>
                    <td>$891.2</td>
                  </tr>
                  <tr>
                    <td>2</td>
                    <td class="productimgname">
                      <a href="productlist.jsp" class="product-img">
                        <img src="${pageContext.request.contextPath}/assets/img/product/product23.jpg" alt="product">
                      </a>
                      <a href="productlist.jsp">iPhone 11</a>
                    </td>
                    <td>$668.51</td>
                  </tr>
                  <tr>
                    <td>3</td>
                    <td class="productimgname">
                      <a href="productlist.jsp" class="product-img">
                        <img src="${pageContext.request.contextPath}/assets/img/product/product24.jpg" alt="product">
                      </a>
                      <a href="productlist.jsp">samsung</a>
                    </td>
                    <td>$522.29</td>
                  </tr>
                  <tr>
                    <td>4</td>
                    <td class="productimgname">
                      <a href="productlist.jsp" class="product-img">
                        <img src="${pageContext.request.contextPath}/assets/img/product/product6.jpg" alt="product">
                      </a>
                      <a href="productlist.jsp">Macbook Pro</a>
                    </td>
                    <td>$291.01</td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card mb-0">
        <div class="card-body">
          <h4 class="card-title">Các đơn đã xử lý</h4>
          <div class="table-responsive dataview">
            <table class="table datatable ">
              <thead>
              <tr>
                <th>SNo</th>
                <th>Product Code</th>
                <th>Product Name</th>
                <th>Brand Name</th>
                <th>Category Name</th>
                <th>Expiry Date</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>1</td>
                <td><a href="javascript:void(0);">IT0001</a></td>
                <td class="productimgname">
                  <a class="product-img" href="productlist.jsp">
                    <img src="${pageContext.request.contextPath}/assets/img/product/product2.jpg" alt="product">
                  </a>
                  <a href="productlist.jsp">Orange</a>
                </td>
                <td>N/D</td>
                <td>Fruits</td>
                <td>12-12-2022</td>
              </tr>
              <tr>
                <td>2</td>
                <td><a href="javascript:void(0);">IT0002</a></td>
                <td class="productimgname">
                  <a class="product-img" href="productlist.jsp">
                    <img src="${pageContext.request.contextPath}/assets/img/product/product3.jpg" alt="product">
                  </a>
                  <a href="productlist.jsp">Pineapple</a>
                </td>
                <td>N/D</td>
                <td>Fruits</td>
                <td>25-11-2022</td>
              </tr>
              <tr>
                <td>3</td>
                <td><a href="javascript:void(0);">IT0003</a></td>
                <td class="productimgname">
                  <a class="product-img" href="productlist.jsp">
                    <img src="${pageContext.request.contextPath}/assets/img/product/product4.jpg" alt="product">
                  </a>
                  <a href="productlist.jsp">Stawberry</a>
                </td>
                <td>N/D</td>
                <td>Fruits</td>
                <td>19-11-2022</td>
              </tr>
              <tr>
                <td>4</td>
                <td><a href="javascript:void(0);">IT0004</a></td>
                <td class="productimgname">
                  <a class="product-img" href="productlist.jsp">
                    <img src="${pageContext.request.contextPath}/assets/img/product/product5.jpg" alt="product">
                  </a>
                  <a href="productlist.jsp">Avocat</a>
                </td>
                <td>N/D</td>
                <td>Fruits</td>
                <td>20-11-2022</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>


<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/jquery.slimscroll.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/dataTables.bootstrap4.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/plugins/apexchart/apexcharts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/apexchart/chart-data.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</body>
</html>