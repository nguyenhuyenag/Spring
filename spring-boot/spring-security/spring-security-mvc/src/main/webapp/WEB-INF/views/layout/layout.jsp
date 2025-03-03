<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="vi">
	<head>
		<sec:csrfMetaTags />
		<link href="#" rel="shortcut icon">
	    <base href="${pageContext.request.contextPath}/"/>
		<!-- JS -->
	    <script src="<c:url value='/static/script/jquery.min.js'/>"></script>
		<script src="<c:url value='/static/script/bootstrap.min.js'/>"></script>
	    <script src="https://momentjs.com/downloads/moment.js"></script>
		<!-- CSS -->
	    <link rel="stylesheet" href="<c:url value='static/css/bootstrap.min.css'/>">
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
	    <c:set var="CONTEXT_PATH" value="${pageContext.request.contextPath}" scope="session"/>
	    <style>
	    	button, input[type=submit] {
				cursor: pointer !important;
			}
			.make-pointer {
				cursor: pointer !important;
			}
	    </style>
		<script>
			$(function() {
				$.ajaxSetup({
					beforeSend: function (xhr, settings) {
						const token = $("meta[name='_csrf']").attr("content");
						const header = $("meta[name='_csrf_header']").attr("content");
						xhr.setRequestHeader(header, token);
					}
				});
			});
		</script>
	</head>
	<body>
	    <div class="container">
		    <tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="body" />
	    </div>
	</body>
</html>
