<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<%@ page session="false" trimDirectiveWhitespaces="true" %>
		<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
			<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
				<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
					<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
						<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
							<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

								<petclinic:layout pageName="medicines">
									<h2>Medicines</h2>
									<table id="medicinesTable" class="table table-striped">
										<thead>
											<tr>
												<th>Name</th>
												<th>Brand</th>
												<th>Edit</th>
												<th>Delete</th>
											</tr>
										</thead>
										<tbody>

											<c:forEach items="${medicines}" var="medicine">
												<input type="hidden" name="id" value="${medicine.id}" />
												<tr>
													<td>
														<c:out value="${medicine.name}" />
													</td>
													<td>
														<c:out value="${medicine.brand}" />
													</td>
													<td>
														<spring:url value="/admin/medicines/{medicineId}/edit" var="addUrl">
															<spring:param name="medicineId" value="${medicine.id}" />
														</spring:url>
														<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">X</a>
													</td>
													<td>
														<jstl:if test="${medicine.used==false}">
															<spring:url value="/admin/medicines/{medicineId}/delete" var="deleteUrl">
																<spring:param name="medicineId" value="${medicine.id}" />
															</spring:url>
															<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete</a>
														</jstl:if>
													</td>
												</c:forEach>
											</tbody>
										</table>


										<spring:url value="/admin/medicines/new" var="addUrl">
										</spring:url>
										<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add medicine</a>
									</petclinic:layout>