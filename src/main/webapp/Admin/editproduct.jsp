<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String cspNonce = (String) request.getAttribute("cspNonce"); %>

<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script nonce="<%= cspNonce %>">
  document.addEventListener("DOMContentLoaded", function() {
    document.title = "Chỉnh sửa thông tin sản phẩm";
    const listStaffElement = document.getElementById("add-product");
    if (listStaffElement) {
      listStaffElement.classList.add("active");
    }
  });
</script>
  <div class="page-wrapper">
    <div class="content">
      <div class="page-header">
        <div class="page-title">
          <h4>Chỉnh sửa sản phẩm</h4>
          <h6>Cập nhập thông tin sản phẩm</h6>
        </div>
      </div>

      <form id="furnitureForm" action="product-controller" method="post" enctype="multipart/form-data">
        <input type="hidden" name="csrfToken" value="${csrfToken}">
        <div class="card">
          <div class="card-body">
            <div class="row">
              <input type="hidden" name="id" value="${furniture.id}">
              <div class="col-lg-3 col-sm-6 col-12">
                <div class="form-group">
                  <label>Tên danh mục</label>
                  <input type="text" value="${furniture.categoryName}" name="furnitureName" readonly>
                </div>
              </div>
              <div class="col-lg-3 col-sm-6 col-12">
                <div class="form-group">
                  <label>Màu sắc</label>
                  <select class="select" name="furnitureColor">
                    <option value="Đen" ${furniture.furnitureColor.equals("Đen") ? 'selected' : ''}>Đen</option>
                    <option value="Nâu" ${furniture.furnitureColor.equals("Nâu") ? 'selected' : ''}>Nâu</option>
                    <option value="Be" ${furniture.furnitureColor.equals("Be") ? 'selected' : ''}>Nâu</option>
                    <option value="Màu tự nhiên" ${furniture.furnitureColor.equals("Màu tự nhiên") ? 'selected' : ''}>Màu tự nhiên</option>
                    <option></option>
                  </select>
                </div>
              </div>
              <div class="col-lg-3 col-sm-6 col-12">
                <div class="form-group">
                  <label>Giá</label>
                  <input type="text" value="${furniture.furniturePrice}" name="furniturePrice">
                </div>
              </div>
              <div class="col-lg-12">
                <div class="form-group">
                  <style nonce="<%= cspNonce %>">
                    .h150{
                      height: 150px;
                    }
                    .image{
                      margin-top: 15px; display: flex; flex-wrap: wrap; gap: 10px;
                    }
                  </style>
                  <label>Mô tả</label>
                  <textarea class="form-control h150" name="furnitureDescription">${furniture.furnitureDescription}</textarea>
                </div>
              </div>
              <div class="col-lg-12">
                <div class="form-group">
                  <label>Ảnh sản phẩm</label>
                  <div class="image-upload">
                    <input type="file" id="image-input" name="images" multiple>
                    <div class="image-uploads">
                      <img src="assets/img/icons/upload.svg" alt="img">
                      <h4>Kéo thả file từ thư mục để thêm ảnh</h4>
                    </div>
                  </div>

                  <div id="uploaded-images" class="image" >
                    <ul class="row img_row">
                      <c:forEach var="imageData" items="${furniture.base64ImageData}" varStatus="status">
                        <li id="image-${furniture.imageID[status.index]}">
                          <div class="productviews">
                            <div class="productviewsimg">
                              <img src="data:image/jpeg;base64,${imageData}" alt="Furniture Image" />
                            </div>
                            <div class="productviewscontent">
                              <input type="hidden" name="existingImageIds" value="${furniture.imageID[status.index]}" />
                              <input type="hidden" name="removedImageIds" id="removedImageIds" value="" />
                              <a href="javascript:void(0);" class="hideset remove-image" data-imageid="${furniture.imageID[status.index]}">x</a>
                            </div>
                          </div>
                        </li>
                      </c:forEach>
                    </ul>
                  </div>
                </div>
              </div>
              <div class="col-lg-12">
                <input type="hidden" name="action" value="editFurniture">
                <input type="submit" value="Update" class="btn btn-submit me-2">
                <a href="product-controller?action=getFurnitureList" class="btn btn-cancel">Cancel</a>
              </div>
            </div>
          </div>
        </div>
      </form>

    </div>
  </div>
</div>
<script nonce="<%= cspNonce %>">
  document.addEventListener('DOMContentLoaded', function () {
    const fileInput = document.getElementById('image-input');
    if (fileInput) {
      fileInput.addEventListener('change', previewImage);
    }
  });

</script>
<script nonce="<%= cspNonce %>">
  document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.remove-image').forEach(function(anchor) {
      anchor.addEventListener('click', function() {
        const imageID = this.getAttribute('data-imageid');
        removeImage(imageID);
      });
    });
  });
  let removedImageIds = [];
  function removeImage(imageId) {
    console.log("Bắt đầu xóa ảnh với ID:", imageId);

    // Kiểm tra nếu ảnh chưa được xóa
    if (!removedImageIds.includes(imageId)) {
      removedImageIds.push(imageId);
      console.log("Danh sách ID đã xóa sau khi thêm:", removedImageIds);
    } else {
      console.log("Ảnh với ID này đã có trong danh sách xóa.");
    }

    // Ẩn ảnh trong UI
    const imageElement = document.querySelector(`#image-${imageId}`);
    if (imageElement) {
      imageElement.style.display = 'none'; // Ẩn ảnh trong giao diện
      console.log(`Ẩn thành công ảnh với ID: ${imageId}`);
    } else {
      console.error(`Không tìm thấy phần tử ảnh với ID: #image-${imageId}`);
    }

    // Cập nhật giá trị của input ẩn
    const hiddenInputs = document.querySelectorAll(`[name="removedImageIds"]`);
    if (hiddenInputs.length > 0) {
      hiddenInputs.forEach(input => {
        input.value = removedImageIds.join(',');
      });
      console.log("Cập nhật input ẩn thành công với giá trị:", removedImageIds.join(','));
    } else {
      console.error("Không tìm thấy input ẩn nào có name='removedImageIds'.");
    }
  }
</script>


<c:import url="footer.jsp"/>