<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
<tiles:getAsString name="title" ignore="true" />
</title>
</head>
<body>
<table border="1" cellpadding="2" cellspacing="2" align="center">

<tr>
<td height="5%" colspan="2"><tiles:insert attribute="header" />
</td>
</tr>

<tr>
<td><tiles:insert attribute="body" /></td>
</tr>

<tr>
<td height="5%" colspan="2"><tiles:insert attribute="footer" />
</td>
</tr>

</table>
</body>
</html>