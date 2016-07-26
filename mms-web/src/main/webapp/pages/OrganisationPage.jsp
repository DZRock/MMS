<%--
  Created by IntelliJ IDEA.
  User: fedor
  Date: 06.06.2016
  Time: 22:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:insertDefinition name="main-layout">
    <t:putAttribute name="title">Орагнизация</t:putAttribute>
    <t:putAttribute name="headers">
        <script>

            $(document).ready(function () {

            });
        </script>
    </t:putAttribute>
    <t:putAttribute name="content">
        <div class="col-md-3">
        </div>
        <div class="col-md-8">
            <div class="row">
                <a href="<c:url value="/"/>"><span class="glyphicon glyphicon-chevron-left"></span>На главную</a>
                <h1>${org.name}</h1>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <ul class="list-group" id="info">
                        <li class="list-group-item">Код : ${org.code}</li>
                        <li class="list-group-item">Адрес : ${org.address}</li>
                    </ul>
                </div>
                <div class="col-md-4">

                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <h1>Техническое обслуживание</h1>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th>Наименование</th>
                            <th>Пользователь</th>
                            <th>Дата последней проверки</th>
                            <th>Статус</th>
                        </tr>
                        </thead>
                        <tbody id="to-content-table">
                        <c:set var="numberto" scope="session" value="0"/>
                        <c:forEach items="${techControls}" var="to">
                            <tr>
                                <td><c:set var="numberto" value="${numberto + 1}" scope="session"/>${numberto}</td>
                                <td><a href="<c:url value="/passport/"/>?type=to&id=${to.id}">${to.name}</a></td>
                                <td>${to.responsibleUser.name}</td>
                                <td> <fmt:formatDate pattern="dd.MM.yyyy" value="${to.lastSupportdate}"/></td>
                                <td>${to.status.name}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <h1>Метрологическое обслуживание</h1>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th>Наименование</th>
                            <th>Пользователь</th>
                            <th>Дата последней проверки</th>
                            <th>Статус</th>
                        </tr>
                        </thead>
                        <tbody id="mo-content-table">
                        <c:set var="numbermo" scope="session" value="0"/>
                        <c:forEach items="${metroControls}" var="mo">
                            <tr>
                                <td><c:set var="numbermo" value="${numbermo + 1}" scope="session"/>${numbermo}</td>
                                <td><a href="<c:url value="/passport/"/>?type=mo&id=${mo.id}">${mo.name}</a></td>
                                <td>${mo.responsibleUser.name}</td>
                                <td> <fmt:formatDate pattern="dd.MM.yyyy" value="${mo.lastSupportdate}"/></td>
                                <td>${mo.status.name}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>