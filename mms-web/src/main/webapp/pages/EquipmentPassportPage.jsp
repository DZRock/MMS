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
    <t:putAttribute name="title">Паспорт оборудования</t:putAttribute>
    <t:putAttribute name="headers">
        <script>

            var id = ${id};

            function getData() {
                requestObject.id=id;
                $.ajax({
                    url:'getData',
                    type:'post',
                    data:requestObject,
                    success:function (data) {

                        var equipment = data.content;

                        var liName = $('<li>').attr('class',"list-group-item").text('Наименование : '+equipment.name);
                        $('#info').append(liName);
                        <%--var liEqu = $('<li>').attr('class',"list-group-item").text('Оборудование : '+'<a href="<c:url value="/equipment/?id="/>'+data.equipment.id+'">'+data.equipment.name+'</a>');--%>
//                        $('#info').append(liEqu);
                        var liResp = $('<li>').attr('class',"list-group-item").text('Ответственный : '+data.responsibleUser.name);
                        $('#info').append(liResp);
                        var liPer = $('<li>').attr('class',"list-group-item").text('Период : '+data.period);
                        $('#info').append(liPer);
                        var liLSD = $('<li>').attr('class',"list-group-item").text('Дата последнего обслуживания : '+data.lastSupportDate);
                        $('#info').append(liLSD);
                        var liStatus = $('<li>').attr('class',"list-group-item").text('Статус : '+data.status);
                        $('#info').append(liStatus);


                        $.each(data.content.checkouts,function (i, value) {
                            var tr = $('<tr>').data('id',value.id);

                            var fileTd = $('<td>');
                            var link = "<c:url value="/passport/getFile?id="/>";
                            $.each(value.files,function (j,file) {

                                fileTd.append($('<a>').attr('href',link+file.id).text(file.name));
                                fileTd.append($('<br>'));
                            });

                            tr.append($('<td>').text(i+1))
                                    .append($('<td>').text(value.comment))
                                    .append($('<td>').text(value.responsibleUser.name))
                                    .append($('<td>').text(value.checkoutDate))
                                    .append(fileTd)
                            ;
                            $('#content-table').append(tr);
                        });
                    }
                });
            }
            $(document).ready(function () {

                getData();

            });
        </script>
    </t:putAttribute>
    <t:putAttribute name="content">
        <div class="col-md-3">
        </div>
        <div class="col-md-8">
            <div class="row">
                <a href="<c:url value="/"/>"><span class="glyphicon glyphicon-chevron-left"></span>На главную</a>
                <h1>Паспорт оборудования</h1>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <ul class="list-group" id="info">
                        <li class="list-group-item">Наименование : ${equip.name}</li>
                        <li class="list-group-item">Серийный номер : ${equip.factoryNumber}</li>
                        <li class="list-group-item">Инвентарный номер : ${equip.inventoryNumber}</li>
                        <li class="list-group-item">Производитель : ${equip.vender.name}, ${equip.vender.country}</li>
                        <li class="list-group-item">Расположение : ${equip.location.name}</li>
                        <li class="list-group-item">Дата принятия : <fmt:formatDate pattern="dd.MM.yyyy" value="${equip.startUpDate}"/></li>
                    </ul>
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
                                <th>Организация</th>
                                <th>Дата последней проверки</th>
                                <th>Статус</th>
                            </tr>
                        </thead>
                        <tbody id="to-content-table">
                            <c:set var="numberto" scope="session" value="0"/>
                            <c:forEach items="${equip.techControls}" var="to">
                                <tr>
                                    <td><c:set var="numberto" value="${numberto + 1}" scope="session"/>${numberto}</td>
                                    <td><a href="<c:url value="/passport/"/>?type=to&id=${to.id}">${to.name}</a></td>
                                    <td>${to.responsibleUser.name}</td>
                                    <td>${to.organisation.name}</td>
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
                            <th>Организация</th>
                            <th>Дата последней проверки</th>
                            <th>Статус</th>
                        </tr>
                        </thead>
                        <tbody id="mo-content-table">
                        <c:set var="numbermo" scope="session" value="0"/>
                        <c:forEach items="${equip.metroControls}" var="mo">
                            <tr>
                                <td><c:set var="numbermo" value="${numbermo + 1}" scope="session"/>${numbermo}</td>
                                <td><a href="<c:url value="/passport/"/>?type=mo&id=${mo.id}">${mo.name}</a></td>
                                <td>${mo.responsibleUser.name}</td>
                                <td>${mo.organisation.name}</td>
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
