<%@ page language="java"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<tiles:insert definition="createTskDef" flush="true">

	<tiles:put name="header" type="string">
		<div style="width: 100%; height: 50px; background-color: #DEB887">
			<h3>Input Task Details:</h3>
		</div>
	</tiles:put>

</tiles:insert>