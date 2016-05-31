<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<t:insertDefinition name="main-layout">
    <t:putAttribute name="title">Панель администратора</t:putAttribute>
    <t:putAttribute name="headers">
        <script>

            $(document).ready(function(){

            });
        </script>
    </t:putAttribute>
    <t:putAttribute name="content">
        <div class="row" style="margin-bottom: 20px">
            <div class="col-md-8">
                <ul class="nav nav-pills">
                    <li><a href="#" class="btn btn-default" data-toggle="modal" data-target="#add-vender-modal">Добавить производителя</a></li>
                    <%--<li><a href="#" class="btn btn-default">Добавить оборудование</a></li>--%>
                    <li><a href="#" class="btn btn-default" data-toggle="modal" data-target="#add-location-modal">Добавить расположение</a></li>
                    <%--<li><a href="#" class="btn btn-default">Добавить ТО</a></li>--%>
                    <%--<li><a href="#" class="btn btn-default">Добавить МО</a></li>--%>

                    <%--<li><a href="/indicators/"><button type="button" class="btn btn-default">Управление показателями</button></a></li>--%>
                </ul>
            </div>
        </div>


        <%--Add vender--%>
        <div id="add-vender-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Добавить производителя</h4>
                    </div>
                    <div class="modal-body">
                        <form role="form" id="add-vender-form">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label for="vender-name">Наименование</label>
                                <input type="text" class="form-control" id="vender-name">
                            </div>
                            <div class="form-group">
                                <label for="vender-country">Страна производитель</label>
                                <input type="text" class="form-control" id="vender-country">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="add-vender-accept">Сохранить</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    </div>
                </div>
            </div>
        </div>

        <%--Add location--%>
        <div id="add-location-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Добавить расположение</h4>
                    </div>
                    <div class="modal-body">
                        <form role="form" id="add-location-form">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label for="location-name">Наименование</label>
                                <input type="text" class="form-control" id="location-name">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="add-location-accept">Сохранить</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>