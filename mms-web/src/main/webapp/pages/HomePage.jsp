<%--
  Created by IntelliJ IDEA.
  User: DZRock
  Date: 01.04.2016
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<t:insertDefinition name="main-layout">
    <t:putAttribute name="title">Главная страница</t:putAttribute>
    <t:putAttribute name="headers">
        <script>

            function updateTOTable(){
                $('#to-table').empty();
                $.ajax({
                    url:'getTOList',
                    type:'get',
                    success: function (data) {
                        $.each(data.content, function (index,value) {
                            var tr = $('<tr>').data('id',value.id);
                            var status = "";
                            var action="";
                            if(value.status!=null){
                                status = value.status.name;
                            }

                            if(value.systemStatus=='need_checkout'){
                                tr.addClass('bg-danger');
                                action = '<a class="check" href="#"><span class="glyphicon glyphicon-ok-sign"></span></a>';
                            }

                            tr.append($('<td>').append(index+1))
                                    .append($('<td>').append(value.name))
                                    .append($('<td>').append(value.equipment.name))
                                    .append($('<td>').append(value.responsibleUser.name))
                                    .append($('<td>').append(value.period))
                                    .append($('<td>').append(value.lastSupportDate))
                                    .append($('<td>').append(status))
                                    .append($('<td>').append(action));
                            $('#to-table').append(tr);
                        })
                    }
                });
            }

            function updateMOTable(){
                $('#mo-table').empty();
                $.ajax({
                    url:'getMOList',
                    type:'get',
                    success: function (data) {
                        $.each(data.content, function (index,value) {
                            var tr = $('<tr>').data('id',value.id);
                            var status = "";
                            if(value.status!=null){
                                status = value.status.name;
                            }
                            if(value.systemStatus=='need_checkout'){
                                tr.addClass('bg-danger');
                            }

                            tr.append($('<td>').append(index+1))
                                    .append($('<td>').append(value.name))
                                    .append($('<td>').append(value.equipment.name))
                                    .append($('<td>').append(value.responsibleUser.name))
                                    .append($('<td>').append(value.period))
                                    .append($('<td>').append(value.lastSupportDate))
                                    .append($('<td>').append(status));
                            $('#mo-table').append(tr);
                        })
                    }
                });
            }

            $(document).ready(function(){
                $('#startUpEquipment').datetimepicker({
                    format: 'DD.MM.YYYY'
                });

                updateTOTable();
                updateMOTable();
//                add equipment init modal
                $('body').on('click','#add-equipment-button', function () {
                    $('#add-equipment-form')[0].reset();
                    $.ajax({
                        url:'getVenders',
                        type:'get',
                        success:function(data){
                            $.each(data.content, function (index,value) {
                                $('#equipment-vender').append($('<option>').attr('value',value.id).append(value.name));
                            });
                            $.ajax({
                                url:'getLocations',
                                type:'get',
                                success: function (data) {
                                    $.each(data.content, function (index,value) {
                                        $('#equipment-location').append($('<option>').attr('value',value.id).append(value.name));
                                    });
                                }
                            });
                        }
                    });
                });
//                add equipment
                $('body').on('click','#add-equipment-accept', function () {
                    requestObject.name=$('#equipment-name').val();
                    requestObject.model=$('#equipment-model').val();
                    requestObject.vender = new Object();
                    requestObject.vender.id=$('#equipment-vender option:selected').val();
                    requestObject.location = new Object();
                    requestObject.location.id=$('#equipment-location option:selected').val();
                    requestObject.startUpDate=$('#equipment-start-up-date').val();
                    requestObject.factoryNumber=$('#equipment-factorynumber').val();
                    $.ajax({
                        url:'addEquipment',
                        contentType:'application/json',
                        data:JSON.stringify(requestObject),
                        type:'post',
                        success:function(data){
                            $('#add-equipment-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                        }
                    });
                });
//                add location
                $('body').on('click','#add-location-accept',function(){
                    requestObject.name=$('#location-name').val();
                    $.ajax({
                        url:'addLocation',
                        type:'post',
                        data:requestObject,
                        success:function(data){
                            $('#add-location-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                        }
                    });
                });
//                add vender
                $('body').on('click','#add-vender-accept',function(){
                    requestObject.name=$('#vender-name').val();
                    requestObject.country=$('#vender-country').val();
                    $.ajax({
                        url:'addVender',
                        type:'post',
                        data:requestObject,
                        success:function(data){
                            $('#add-vender-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                        }
                    });
                });

//            add to button
                $('body').on('click','#add-to-button', function () {
                    $('#add-to-form')[0].reset();
                    $.ajax({
                        url:'getUsersMinimal',
                        type:'get',
                        success: function (data) {
                            $.each(data.content, function (index,value) {
                                $('#to-user').append($('<option>').attr('value',value.id).text(value.name));
                            });
                            $.ajax({
                                url:'getEquipmentsMinimal',
                                type:'get',
                                success: function (data) {
                                    $.each(data.content, function (index,value) {
                                        $('#to-equipment').append($('<option>').attr('value',value.id).text(value.name));
                                    });
                                }
                            });
                        }
                    });
                });
//            add mo button
                $('body').on('click','#add-mo-button', function () {
                    $('#add-mo-form')[0].reset();
                    $.ajax({
                        url:'getUsersMinimal',
                        type:'get',
                        success: function (data) {
                            $.each(data.content, function (index,value) {
                                $('#mo-user').append($('<option>').attr('value',value.id).text(value.name));
                            });
                            $.ajax({
                                url:'getEquipmentsMinimal',
                                type:'get',
                                success: function (data) {
                                    $.each(data.content, function (index,value) {
                                        $('#mo-equipment').append($('<option>').attr('value',value.id).text(value.name));
                                    });
                                }
                            });
                        }
                    });
                });
//                add to
                $('body').on('click','#add-to-accept', function () {
                    requestObject.responsibleUser = new Object();
                    requestObject.responsibleUser.id=$('#to-user option:selected').val();
                    requestObject.equipment = new Object();
                    requestObject.equipment.id = $('#to-equipment option:selected').val();
                    requestObject.period=$('#to-period').val();
                    requestObject.instruments=$('#to-instruments').val();
                    requestObject.expendableMaterial=$('#to-material').val();
                    $.ajax({
                        url:"addTo",
                        type:'post',
                        data:JSON.stringify(requestObject),
                        contentType:'application/json',
                        success: function (data) {
                            $('#add-to-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                            updateTOTable();
                        }
                    });
                });
//                add mo
                $('body').on('click','#add-mo-accept', function () {
                    requestObject.responsibleUser = new Object();
                    requestObject.responsibleUser.id=$('#mo-user option:selected').val();
                    requestObject.equipment = new Object();
                    requestObject.equipment.id = $('#mo-equipment option:selected').val();
                    requestObject.period=$('#mo-period').val();
                    $.ajax({
                        url:'addMo',
                        type:'post',
                        data:JSON.stringify(requestObject),
                        contentType:'application/json',
                        success: function (data) {
                            $('#add-mo-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                            updateMOTable();
                        }
                    });
                });
//                do to checkout button
                $('body').on('click','.check', function () {
                    $('#do-to-checkout-form')[0].reset();
                    var id = $(this).closest('tr').data('id');
                    $('#to-checkouted-id').val(id);
                    $('#do-to-checkout-modal').modal();
                });
//                do to checkout
                $('body').on('click','#to-checkout-accept', function () {
                    requestObject.id=$('#to-checkouted-id').val();
                    requestObject.msg=$('#to-checkout-msg').val();
                    $.ajax({
                        url:'checkoutTo',
                        data:requestObject,
                        type:'post',
                        success: function (data) {
                            $('#do-to-checkout-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                            updateTOTable();
                        }
                    });
                });
            });
        </script>
    </t:putAttribute>
    <t:putAttribute name="content">
        <div class="col-md-3">
            <div class="row">
                <div class="center-block">
                    <div class="panel panel-default">
                        <div class="panel-body center-block">
                            ТО и МО
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="panel-group" id="accordion">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                    Техническое обслуживание
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <button class="btn btn-default btn-lg btn-block">Загрузить данные</button>
                                <button class="btn btn-default btn-lg btn-block">Сохранить</button>
                                <button class="btn btn-default btn-lg btn-block">Установить напоминание</button>
                                <select class="form-control" placeholder="Сортировать по">
                                    <option>По дате проведения</option>
                                    <option>По типу оборудования</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                    Метрологическое обслуживание
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse">
                            <div class="panel-body">
                                <button class="btn btn-default btn-lg btn-block">Загрузить данные</button>
                                <button class="btn btn-default btn-lg btn-block">Сохранить</button>
                                <button class="btn btn-default btn-lg btn-block">Установить напоминание</button>
                                <select class="form-control" placeholder="Сортировать по">
                                    <option>По дате проведения</option>
                                    <option>По типу оборудования</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                        Администрирование
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseThree" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <button class="btn btn-default btn-lg btn-block" id="add-equipment-button" data-toggle="modal" data-target="#add-equipment-modal">Добавить оборудование</button>
                                    <button class="btn btn-default btn-lg btn-block" data-toggle="modal" data-target="#add-vender-modal">Добавить производителя</button>
                                    <button class="btn btn-default btn-lg btn-block" data-toggle="modal" data-target="#add-location-modal">Добавить расположение</button>
                                    <button class="btn btn-default btn-lg btn-block" id="add-to-button" data-toggle="modal" data-target="#add-to-modal">Запустить техническое обслуживание</button>
                                    <button class="btn btn-default btn-lg btn-block" id="add-mo-button" data-toggle="modal" data-target="#add-mo-modal">Запустить метрологическое обслуживание</button>
                                </div>
                            </div>
                        </div>
                    </security:authorize>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-body">
                        ${pageContext.request.userPrincipal.principal.name}
                            <a href="<c:url value="login/logout"/>">Выход</a>
                    </div>
                </div>
            </div>
            <div class="row">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#to-tab">Техническое обслуживание</a></li>
                    <li><a data-toggle="tab" href="#mo-tab">Метрологическое обслуживание</a></li>
                </ul>

                <div class="tab-content">
                    <div id="mo-tab" class="tab-pane fade">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>№</th>
                                <th>Наименование</th>
                                <th>Модель</th>
                                <th>Ответственный</th>
                                <th>Период (дней)</th>
                                <th>Дата последней поверки</th>
                                <th>Статус</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="mo-table"></tbody>
                        </table>
                    </div>
                    <div id="to-tab" class="tab-pane fade in active">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>№</th>
                                        <th>Наименование</th>
                                        <th>Модель</th>
                                        <th>Ответственный</th>
                                        <th>Период (дней)</th>
                                        <th>Дата последней поверки</th>
                                        <th>Статус</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody id="to-table"></tbody>
                            </table>
                    </div>
                </div>
            </div>
        </div>

        <%--do to checkout--%>
        <div id="do-to-checkout-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Провести техническое обслуживание</h4>
                    </div>
                    <div class="modal-body">
                        <form role="form" id="do-to-checkout-form">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" id="to-checkouted-id" value=""/>
                            <div class="form-group">
                                <label for="to-checkout-msg">Комментарий</label>
                                <input type="text" class="form-control" id="to-checkout-msg">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="to-checkout-accept">Провести</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    </div>
                </div>
            </div>
        </div>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <%--Add equipment--%>
            <div id="add-equipment-modal" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Добавить оборудование</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" id="add-equipment-form">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-group">
                                    <label for="equipment-name">Наименование</label>
                                    <input type="text" class="form-control" id="equipment-name">
                                </div>
                                <div class="form-group">
                                    <label for="equipment-model">Модель</label>
                                    <input type="text" class="form-control" id="equipment-model">
                                </div>
                                <div class="form-group">
                                    <label for="equipment-factorynumber">Серийный номер</label>
                                    <input type="text" class="form-control" id="equipment-factorynumber">
                                </div>
                                <div class="form-group">
                                    <label for="equipment-vender">Производитель</label>
                                    <select id="equipment-vender" class="form-control"></select>
                                </div>
                                <div class="form-group">
                                    <label for="equipment-location">Расположение</label>
                                    <select id="equipment-location" class="form-control"></select>
                                </div>
                                <div class="form-group">
                                    <label for="equipment-start-up-date">Дата принятия</label>
                                    <div class='input-group date' id="startUpEquipment">
                                        <input type='text' class="form-control" id="equipment-start-up-date"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar">
                                        </span>
                                    </span>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" id="add-equipment-accept">Сохранить</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
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
            <%--add to modal--%>
            <div id="add-to-modal" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Запустить техническое обслуживание</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" id="add-to-form">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-group">
                                    <label for="to-user">Ответственный</label>
                                    <select class="form-control" id="to-user"></select>
                                </div>
                                <div class="form-group">
                                    <label for="to-equipment">Оборудование</label>
                                    <select class="form-control" id="to-equipment"></select>
                                </div>
                                <div class="form-group">
                                    <label for="to-period">Период (дней)</label>
                                    <input type="number" class="form-control" id="to-period">
                                </div>
                                <div class="form-group">
                                    <label for="to-material">Необходимые материалы</label>
                                    <textarea class="form-control" id="to-material"></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="to-instruments">Необходимые инструменты</label>
                                    <textarea class="form-control" id="to-instruments"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" id="add-to-accept">Запустить</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
                </div>
            </div>
            <%--add mo modal--%>
            <div id="add-mo-modal" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Запустить метрологическое обслуживание</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" id="add-mo-form">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-group">
                                    <label for="mo-user">Ответственный</label>
                                    <select class="form-control" id="mo-user"></select>
                                </div>
                                <div class="form-group">
                                    <label for="mo-equipment">Оборудование</label>
                                    <select class="form-control" id="mo-equipment"></select>
                                </div>
                                <div class="form-group">
                                    <label for="mo-period">Период (дней)</label>
                                    <input type="number" class="form-control" id="mo-period">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" id="add-mo-accept">Запустить</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
                </div>
            </div>
        </security:authorize>
    </t:putAttribute>
</t:insertDefinition>