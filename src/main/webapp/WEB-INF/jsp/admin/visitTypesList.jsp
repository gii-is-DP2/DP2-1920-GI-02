<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<%@ page session="false" trimDirectiveWhitespaces="true" %>
		<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
			<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
				<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
					<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
						<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
							<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

								<petclinic:layout pageName="visitTypes">
									<h2>Visit types</h2>
									<table id="visitTypesTable" class="table table-striped">
										<thead>
											<tr>
												<th>Name</th>
												<th>Duration</th>
												<th>Price</th>
												<th>Edit</th>
											</tr>
										</thead>
										<tbody>

											<c:forEach items="${visitTypes}" var="visitType">
												<input type="hidden" name="id" value="${visitType.id}" />
												<tr>
													<td>
														<c:out value="${visitType.name}" />
													</td>
													<td>
														<c:out value="${visitType.duration}" />
													</td>
													<td>
														<c:out value="${visitType.price}" />
													</td>
													<td>
														<spring:url value="/admin/visitTypes/{visitTypeId}/edit" var="addUrl">
															<spring:param name="visitTypeId" value="${visitType.id}" />
														</spring:url>
														<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">X</a>
													</td>
												</c:forEach>
											</tbody>
										</table>


										<spring:url value="/admin/visitTypes/new" var="addUrl">
										</spring:url>
										<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add visit type</a>
									</petclinic:layout>