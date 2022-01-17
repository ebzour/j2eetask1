<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<%@ page isELIgnored="false"%>

<c:if test="${empty user}">
	<c:set var="headerMsg" scope="request"
		value="Input Login Credentials :" />
	<jsp:forward page="/loginDef.do"></jsp:forward>
</c:if>

