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

<t:insertDefinition name="main-layout">
    <t:putAttribute name="title">Паспорт</t:putAttribute>
    <t:putAttribute name="headers">
        <script>

            var id = ${id};
            var type = '${type}';

            function getData() {
                requestObject.id=id;
                $.ajax({
                    url:'getData'+type,
                    type:'post',
                    data:requestObject,
                    success:function (data) {

                        var liName = $('<li>').attr('class',"list-group-item").text('Наименование : '+data.content.name);
                        $('#info').append(liName);
                        var liEqu = $('<li>').attr('class',"list-group-item").html('Оборудование : '+'<a href="<c:url value="/equipment/?id="/>'+data.content.equipment.id+'">'+data.content.equipment.name+'</a>');
                        $('#info').append(liEqu);
                        var liResp = $('<li>').attr('class',"list-group-item").text('Ответственный : '+data.content.responsibleUser.name);
                        $('#info').append(liResp);
                        var liPer = $('<li>').attr('class',"list-group-item").text('Период : '+data.content.period);
                        $('#info').append(liPer);
                        var liLSD = $('<li>').attr('class',"list-group-item").text('Дата последнего обслуживания : '+data.content.lastSupportDate);
                        $('#info').append(liLSD);
                        var liStatus = $('<li>').attr('class',"list-group-item").text('Текущий статус : '+data.content.status.name);
                        $('#info').append(liStatus);
                        var liOrg = $('<li>').attr('class',"list-group-item").text('Организация : '+data.content.organisation.name);
                        $('#info').append(liOrg);


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
                <c:if test="${type=='mo'}">
                    <h1>Паспорт метрологического обслуживания</h1>
                </c:if>
                <c:if test="${type=='to'}">
                    <h1>Паспорт технического обслуживания</h1>
                </c:if>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <ul class="list-group" id="info">

                    </ul>
                </div>
                <div class="col-md-4">
                    <div class="row">
                        <a href="<c:url value="/passport/getXlsx"/>?id=${id}&type=${type}" class="btn btn-default">Скачать в xlsx</a>
                    </div>
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <div class="row">
                            <a href="<c:url value="/passport/deleteControl"/>?id=${id}&type=${type}">Удалить <span class="glyphicon glyphicon-remove"></span></a>
                        </div>
                        <div class="row">
                            <a href="<c:url value="/passport/changeStatus"/>?id=${id}&type=${type}">Изменить статус <span class="glyphicon glyphicon-refresh"></span></a>
                        </div>
                    </security:authorize>
                </div>
            </div>
            <div class="row">
                <div class="row">

                    <table class="table">
                        <thead>
                            <tr>
                                <th>№</th>
                                <th>Сообщение</th>
                                <th>Пользователь</th>
                                <th>Дата</th>
                                <th>Файлы</th>
                            </tr>
                        </thead>
                        <tbody id="content-table">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
