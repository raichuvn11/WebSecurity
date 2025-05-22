<% String cspNonce = (String) request.getAttribute("cspNonce"); %>
<script nonce="<%= cspNonce %>">
    (function(){
        const originalCreateElement = document.createElement;
        document.createElement = function(tagName) {
            const el = originalCreateElement.call(document, tagName);
            if (tagName.toLowerCase() === 'style') {
                el.setAttribute('nonce', '<%= cspNonce %>');
            }
            return el;
        }
    })();
</script>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/select2/js/select2.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/select2/js/custom-select.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/dataTables.bootstrap4.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/owlcarousel/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/apexchart2/apexcharts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/apexchart2/chart-data.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalerts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert2/sweetalerts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
<script src="${pageContext.request.contextPath}/scripts/main.js"></script>
</body>
</html>