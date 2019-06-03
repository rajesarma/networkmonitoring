<%@ page import="java.util.Calendar, java.util.GregorianCalendar" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Sakshi Network Status</title>

	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/perfect-scrollbar.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/util.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">

	<%
		response.setIntHeader("Refresh", 10);
		Calendar calendar = new GregorianCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String CT = day + "-" + month + "-" + year + " " + hour + ":" + minute + ":" +
				second;
	%>

</head>
<body>


	<c:if test="${not empty locations}">
	<%--<img src="/images/sakshi.jpg">--%>

	<div class="limiter">
		<div class="container-table100">
			<div class="wrap-table100">
				<div class="table100 ver1 m-b-110">
					<div class="table100-head">
						<table>
							<thead>
								<tr class="row100 head">
									<th class="column100 column1" >Location</th>
									<th class="column100 column2" style="text-align: center">GE Port</th>
									<th class="column100 column3" style="text-align: center">Control Port</th>
									<th class="column100 column4" style="text-align: center">WAN IP - PE</th>
									<th class="column100 column5" style="text-align: center">WAN IP - CPE</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="table100-body js-pscroll ps ps--active-y">
						<table>
						<tbody>
							<c:forEach items="${locations }" var="site" >
								<tr class="row100">
									<td class="column100 column1" >
										<c:out value="${site.key}"/>
									</td>
									<c:forEach items="${site.value}" var="location" varStatus="loop">
										<td
											class="column100 column${loop.index + 2}"
												<c:choose>
													<c:when test="${location.value eq'UP'}">
														style='background-color:#00af00; color: white; text-align: center'
													</c:when>
													<c:when test="${location.value
																eq'DOWN'}">
														style='background-color:red;color: white; text-align: center'
													</c:when>

													<c:when test="${location.value
																eq'PACKET_LOSS'}">
														style='background-color:yellow;color: white; text-align: center'
													</c:when>
												</c:choose>
										>
											<c:out value="${location.key}"/>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="5" style="text-align: right; font-size: 14px">Last updated on <%=CT %>
								</td>
							</tr>
						</tfoot>
					</table>
				</c:if>

			<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/js/popper.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/js/perfect-scrollbar.min.js" type="text/javascript"></script>
			<script type="text/javascript">
				$('.js-pscroll').each(function(){
					var ps = new PerfectScrollbar(this);

					$(window).on('resize', function(){
						ps.update();
					})
				});

				(function ($) {
					"use strict";
					$('.column100').on('mouseover',function(){
						var table1 = $(this).parent().parent().parent();
						var table2 = $(this).parent().parent();
						var verTable = $(table1).data('vertable')+"";
						var column = $(this).data('column') + "";

						$(table2).find("."+column).addClass('hov-column-'+ verTable);
						$(table1).find(".row100.head ."+column).addClass('hov-column-head-'+ verTable);
					});

					$('.column100').on('mouseout',function(){
						var table1 = $(this).parent().parent().parent();
						var table2 = $(this).parent().parent();
						var verTable = $(table1).data('vertable')+"";
						var column = $(this).data('column') + "";

						$(table2).find("."+column).removeClass('hov-column-'+ verTable);
						$(table1).find(".row100.head ."+column).removeClass('hov-column-head-'+ verTable);
					});
				})(jQuery);
			</script>
	</body>
</html>