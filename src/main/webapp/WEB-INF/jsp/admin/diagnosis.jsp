<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="visits">
    <h2>Data diagnosis of that visit</h2>
    <table id="visitsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Prescriptions</th>
        </tr>
        </thead>
        <tbody>
            <tr>
            	<td>
            		<c:out value="${visit.diagnosis.date}"/>
                </td>
            	<td>
                    <c:out value="${visit.diagnosis.description}"/>
                </td>
                <td>
                    <table id="prescriptionTable" class="table table-striped">
      				  <thead>
       					 <tr>
         				   	<th>Medicine</th>
           				 	<th>Frequency</th>
            				<th>Duration</th>
        				</tr>
        			  </thead>
        			  <tbody>
        			  	<c:forEach items="${visit.diagnosis.prescriptions}" var="prescriptions">
	            			<tr>
	            				<td>
	                    			<c:out value="${prescriptions.medicine.name}"/>
	                			</td>
	                			<td>
	                    			<c:out value="${prescriptions.frequency}"/>
	                			</td>
	                			<td>
	                    			<c:out value="${prescriptions.duration}"/>
	                			</td>
	                		</tr>
	                	</c:forEach>
                	  </tbody>
                	 </table>
                			</td>
			            </tr>
			        </tbody>
			    </table>
</petclinic:layout>