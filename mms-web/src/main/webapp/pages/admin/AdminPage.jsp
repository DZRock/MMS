<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<t:insertDefinition name="main-layout">
    <t:putAttribute name="title">Панель администратора</t:putAttribute>
    <t:putAttribute name="headers">

    </t:putAttribute>
    <t:putAttribute name="content">
        <div class="row" style="margin-bottom: 20px">
            <div class="col-md-8">
                <ul class="nav nav-pills">
                    <li><a href="indicators/" class="btn btn-default">Управление показателями</a></li>
                    <li><a href="users/" class="btn btn-default">Управление пользователями</a></li>
                    <%--<li><a href="/indicators/"><button type="button" class="btn btn-default">Управление показателями</button></a></li>--%>
                </ul>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>