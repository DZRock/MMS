<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title><t:insertAttribute name="title"/></title>
    <script   src="https://code.jquery.com/jquery-2.2.2.min.js"   integrity="sha256-36cp2Co+/62rEAAYHLmRCPIych47CvdM+uTBJwSzWjI="   crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/core.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bower/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css/">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    <script src="https://code.highcharts.com"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bower/moment/min/moment.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bower/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
    <script>
        $(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function(e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });

        var requestObject = {${_csrf.parameterName}:"${_csrf.token}"};

        $(document).ready(function(){

        });

    </script>
    <t:insertAttribute name="headers"/>
</head>
<body>
    <div>
        <div class="col-md-3">
            <div class="panel panel-default">
                <div class="panel-heading">Данные о пользователе</div>
                <ul class="list-group">
                    <li class="list-group-item">
                        <a href="#" class="btn btn-lg" data-toggle="collapse" data-target="#userdata">Данные о пользователе</a>
                        <div id="userdata" class="collapse">
                            <ul>
                                <li>${pageContext.request.userPrincipal.principal.name}</li>
                                <li>${pageContext.request.userPrincipal.principal.email}</li>
                                <li>${pageContext.request.userPrincipal.principal.birthDate}</li>
                            </ul>
                        </div>
                    </li>
                    <li class="list-group-item">
                        <a href="#" class="btn btn-lg" data-toggle="collapse" data-target="#indicatorsInfo">Информация о показателях</a>
                        <div class="collapse" id="indicatorsInfo">
                            <ul id="indicatorsList">
                            </ul>
                        </div>
                    </li>
                    <li class="list-group-item">
                        <a type="button" class="btn btn-lg" data-toggle="modal" data-target="#add-value-modal" id="add-value-show">Зарегестрировать значение</a>
                    </li>
                    <li class="list-group-item">
                        <a type="button" class="btn btn-lg" data-toggle="modal" data-target="#edit-dashboard-modal" id="edit-dash-show">Настроить дашборд</a>
                    </li>
                    <li class="list-group-item">
                        <a href="${pageContext.request.contextPath}/logout" class="btn btn-lg">Выйти</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="col-md-8">
            <t:insertAttribute name="content"/>
        </div>
    </div>


</body>
</html>
