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

            var sortTo = 'up';
            var sortMo = 'up';

            function updateTOTable(sort){
                $('#to-table').empty();
                requestObject.sort=sort;
                $.ajax({
                    url:'getTOList',
                    type:'get',
                    data:requestObject,
                    success: function (data) {
                        $.each(data.content, function (index,value) {
                            var tr = $('<tr>').data('id',value.id);
                            var status = "";
                            var action="";
                            if(value.status!=null){
                                status = value.status.name;
                            }

                            if(value.systemStatus=='need_checkout_to'){
                                tr.addClass('bg-danger');
                                action = '<a class="check to" href="#"><span class="glyphicon glyphicon-ok-sign"></span></a>';
                            }

                            var psLink = '<a href="<c:url value="/passport/?type=to&id="/>'+tr.data("id")+'">'+value.name+'</a>';
                            var eqLink = '<a href="<c:url value="/equipment/?id="/>'+value.equipment.id+'">'+value.equipment.name+'</a>';
                            var orgLink = '<a href="<c:url value="/organisation/"/>'+value.organisation.id+'">'+value.organisation.name+'</a>';

                            tr.append($('<td>').append(index+1))
                                    .append($('<td>').append(psLink))
                                    .append($('<td>').append(eqLink))
                                    .append($('<td>').append(value.responsibleUser.name))
                                    .append($('<td>').append(orgLink))
                                    .append($('<td>').append(value.period))
                                    .append($('<td>').append(value.lastSupportDate))
                                    .append($('<td>').append(status))
                                    .append($('<td>').append(action));
                            $('#to-table').append(tr);
                        })
                    }
                });
            }

            function updateMOTable(sort){
                $('#mo-table').empty();
                requestObject.sort=sort;
                $.ajax({
                    url:'getMOList',
                    type:'get',
                    data:requestObject,
                    success: function (data) {
                        $.each(data.content, function (index,value) {
                            var tr = $('<tr>').data('id',value.id);
                            var status = "";
                            var action="";
                            if(value.status!=null){
                                status = value.status.name;
                            }
                            if(value.systemStatus=='need_checkout_mo'){
                                tr.addClass('bg-danger');
                                action = '<a class="check mo" href="#"><span class="glyphicon glyphicon-ok-sign"></span></a>';

                            }

                            var psLink = '<a href="<c:url value="/passport/?type=mo&id="/>'+tr.data("id")+'">'+value.name+'</a>';
                            var eqLink = '<a href="<c:url value="/equipment/?id="/>'+value.equipment.id+'">'+value.equipment.name+'</a>';
                            var orgLink = '<a href="<c:url value="/organisation/"/>'+value.organisation.id+'">'+value.organisation.name+'</a>';

                            tr.append($('<td>').append(index+1))
                                    .append($('<td>').append(psLink))
                                    .append($('<td>').append(eqLink))
//                                    .append($('<td>').append(value.equipment.name))
                                    .append($('<td>').append(value.responsibleUser.name))
                                    .append($('<td>').append(orgLink))
                                    .append($('<td>').append(value.period))
                                    .append($('<td>').append(value.lastSupportDate))
                                    .append($('<td>').append(status))
                                    .append($('<td>').append(action));
                            $('#mo-table').append(tr);
                        })
                    }
                });
            }

            $(document).ready(function(){
                $('#startUpEquipment').datetimepicker({
                    format: 'DD.MM.YYYY'
                });

                updateTOTable('up');
                updateMOTable('up');

                $('body').on('click','#sort-to-date',function () {
                    if(sortTo=='up'){
                        sortTo='down';
                        updateTOTable(sortTo);
                    }else{
                        sortTo='up';
                        updateTOTable(sortTo);
                    }
                });
                $('body').on('click','#sort-mo-date',function () {
                    if(sortMo=='up'){
                        sortMo='down';
                        updateMOTable(sortMo);
                    }else{
                        sortMo='up';
                        updateMOTable(sortMo);
                    }
                });


//                add equipment init modal
                $('body').on('click','#add-equipment-button', function () {
                    $('#add-equipment-form')[0].reset();
                    $.ajax({
                        url:'getVenders',
                        type:'get',
                        success:function(data){
                            $('#equipment-vender').empty();
                            $.each(data.content, function (index,value) {
                                $('#equipment-vender').append($('<option>').attr('value',value.id).append(value.name));
                            });
                            $.ajax({
                                url:'getLocations',
                                type:'get',
                                success: function (data) {
                                    $('#equipment-location').empty();
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
                    requestObject.vender = new Object();
                    requestObject.vender.id=$('#equipment-vender option:selected').val();
                    requestObject.location = new Object();
                    requestObject.location.id=$('#equipment-location option:selected').val();
                    requestObject.startUpDate=$('#equipment-start-up-date').val();
                    requestObject.factoryNumber=$('#equipment-factorynumber').val();
                    requestObject.inventoryNumber=$('#equipment-inventorynumbe').val();
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
//               add to button
                $('body').on('click','#add-to-button', function () {
                    $('#add-to-form')[0].reset();
                    $.ajax({
                        url:'getUsersMinimal',
                        type:'get',
                        success: function (data) {
                            $('#to-user').empty();
                            $.each(data.content, function (index,value) {
                                $('#to-user').append($('<option>').attr('value',value.id).text(value.name));
                            });
                            $.ajax({
                                url:'getEquipmentsMinimal',
                                type:'get',
                                success: function (data) {
                                    $('#to-equipment').empty();
                                    $.each(data.content, function (index,value) {
                                        $('#to-equipment').append($('<option>').attr('value',value.id).text(value.name));
                                    });
                                    $.ajax({
                                        url:'<c:url value="/getOrganisations"/>',
                                        type:'get',
                                        success:function (data) {
                                            $('#to-organisation').empty();
                                            $.each(data.content, function (index,value) {
                                                $('#to-organisation').append($('<option>').attr('value',value.id).text(value.name));
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                });
//              add organisation button
                $('body').on('click','#add-organisation-accept',function () {
                    requestObject.name=$('#organisation-name').val();
                    requestObject.address=$('#organisation-address').val();
                    requestObject.code=$('#organisation-code').val();
                    $.ajax({
                        url:'<c:url value="/addOrganisation"/>',
                        type:'post',
                        contentType:'application/json',
                        data:JSON.stringify(requestObject),
                        success:function (data) {
                            $('#add-organisation-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                        }
                    });
                });
//               add mo button
                $('body').on('click','#add-mo-button', function () {
                    $('#add-mo-form')[0].reset();
                    $.ajax({
                        url:'getUsersMinimal',
                        type:'get',
                        success: function (data) {
                            $('#mo-user').empty();
                            $.each(data.content, function (index,value) {
                                $('#mo-user').append($('<option>').attr('value',value.id).text(value.name));
                            });
                            $.ajax({
                                url:'getEquipmentsMinimal',
                                type:'get',
                                success: function (data) {
                                    $('#mo-equipment').empty();
                                    $.each(data.content, function (index,value) {
                                        $('#mo-equipment').append($('<option>').attr('value',value.id).text(value.name));
                                    });
                                    $.ajax({
                                        url:'<c:url value="/getOrganisations"/>',
                                        type:'get',
                                        success:function (data) {
                                            $('#mo-organisation').empty();
                                            $.each(data.content, function (index,value) {
                                                $('#mo-organisation').append($('<option>').attr('value',value.id).text(value.name));
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                });
//                add to
                $('body').on('click','#add-to-accept', function () {
                    requestObject.responsibleUser = new Object();
                    requestObject.name = $('#to-name').val();
                    requestObject.responsibleUser.id=$('#to-user option:selected').val();
                    requestObject.equipment = new Object();
                    requestObject.equipment.id = $('#to-equipment option:selected').val();
                    requestObject.period=$('#to-period').val();
                    requestObject.instruments=$('#to-instruments').val();
                    requestObject.expendableMaterial=$('#to-material').val();
                    requestObject.organisation={};
                    requestObject.organisation.id=$('#to-organisation option:selected').val();
                    $.ajax({
                        url:"addTo",
                        type:'post',
                        data:JSON.stringify(requestObject),
                        contentType:'application/json',
                        success: function (data) {
                            $('#add-to-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                            updateTOTable(sortTo);
                        }
                    });
                });
//                add mo
                $('body').on('click','#add-mo-accept', function () {
                    requestObject.responsibleUser = new Object();
                    requestObject.name = $('#mo-name').val();
                    requestObject.responsibleUser.id=$('#mo-user option:selected').val();
                    requestObject.equipment = new Object();
                    requestObject.equipment.id = $('#mo-equipment option:selected').val();
                    requestObject.period=$('#mo-period').val();
                    requestObject.organisation={};
                    requestObject.organisation.id=$('#mo-organisation option:selected').val();
                    $.ajax({
                        url:'addMo',
                        type:'post',
                        data:JSON.stringify(requestObject),
                        contentType:'application/json',
                        success: function (data) {
                            $('#add-mo-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                            updateMOTable(sortMo);
                        }
                    });
                });
//                do to checkout button
                $('body').on('click','.check', function () {
                    $('#do-to-checkout-form')[0].reset();
                    var id = $(this).closest('tr').data('id');
                    var type = $(this).hasClass("to");
                    if(type){
                        $('#to-checkout-header').text("Проверить техническое обеспечение");
                        $('#to-checkout-accept').text("Проверить");
                        $('#check-type').val("to");
                    }else{
                        $('#to-checkout-header').text("Поверить метрологическое обеспечение");
                        $('#to-checkout-accept').text("Поверить");
                        $('#check-type').val("mo");
                    }
                    $('#to-checkouted-id').val(id);
                    $('#do-to-checkout-modal').modal();
                });
//                do to checkout
                $('body').on('click','#to-checkout-accept', function () {
                    var type = $(this).hasClass("to");
                    if(type){
                        requestObject.type="to";
                        $('#to-checkout-accept').removeClass("to");
                    }else{
                        requestObject.type="mo";
                        $('#to-checkout-accept').removeClass("mo");
                    }

                    var form = $('#do-to-checkout-form')[0];
                    var formData = new FormData(form);

                    $.ajax({
                        data:formData,
                        url:"check",
                        type:"post",
                        processData: false,  // tell jQuery not to process the data
                        contentType: false,   // tell jQuery not to set contentType
                        success:function(data){
                            $('#do-to-checkout-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                            updateTOTable(sortTo);
                            updateMOTable(sortMo);
                        }
                    });
                });

                $('body').on('click','#add-to-xlsx-accept',function () {
                    var formData = new FormData($('#add-to-xlsx')[0]);
                    $.ajax({
                        data:formData,
                        url:"uploadToXlsx",
                        type:"post",
                        processData: false,  // tell jQuery not to process the data
                        contentType: false,   // tell jQuery not to set contentType
                        success:function(data){
                            $('#add-to-xlsx-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                            updateTOTable(sortTo);
                            updateMOTable(sortMo);
                        }
                    });
                });

                $('body').on('click','#add-mo-xlsx-accept',function () {
                    var formData = new FormData($('#add-mo-xlsx')[0]);
                    $.ajax({
                        data:formData,
                        url:"<c:url value="uploadMoXlsx"/>",
                        type:"post",
                        processData: false,  // tell jQuery not to process the data
                        contentType: false,   // tell jQuery not to set contentType
                        success:function(data){
                            $('#add-mo-xlsx-modal').modal('hide');
                            $('#msg-body').text(data.text);
                            $('#msg-modal').modal();
                            updateTOTable(sortTo);
                            updateMOTable(sortMo);
                        }
                    });
                });

                $('body').on('click','#add-file-block',function () {
                    var div = $('<div>').attr("class","input-group");
                    var span = $("<span>").attr("class","glyphicon glyphicon-remove file-remove");
                    var block = $('<input>').attr("name","files[]").attr("class","files").attr('type','file').attr("title","Выбор файла");

                    div.append(span);
                    div.append(block);

                    $('#files-block').append(div);
                });

                $('body').on("click",".file-remove",function () {
                    $(this).closest('div').remove();
                });

            });
        </script>
    </t:putAttribute>
    <t:putAttribute name="content">
        <div class="col-md-2">
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
                                <button class="btn btn-default btn-block" data-toggle="modal" data-target="#add-to-xlsx-modal">Загрузить данные</button>
                                <%--<button class="btn btn-default btn-block">Сохранить</button>--%>
                                <%--<button class="btn btn-default btn-block">Установить напоминание</button>--%>
                                <%--<select class="form-control" placeholder="Сортировать по">--%>
                                    <%--<option>По дате проведения</option>--%>
                                    <%--<option>По типу оборудования</option>--%>
                                <%--</select>--%>
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
                                <button class="btn btn-default btn-block" data-toggle="modal" data-target="#add-mo-xlsx-modal">Загрузить данные</button>
                                <%--<button class="btn btn-default btn-block">Сохранить</button>--%>
                                <%--<button class="btn btn-default btn-block">Установить напоминание</button>--%>
                                <%--<select class="form-control" placeholder="Сортировать по">--%>
                                    <%--<option>По дате проведения</option>--%>
                                    <%--<option>По типу оборудования</option>--%>
                                <%--</select>--%>
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
                                    <button class="btn btn-default btn-block" id="add-equipment-button" data-toggle="modal" data-target="#add-equipment-modal">Добавить оборудование</button>
                                    <button class="btn btn-default btn-block" data-toggle="modal" data-target="#add-vender-modal">Добавить производителя</button>
                                    <button class="btn btn-default btn-block" data-toggle="modal" data-target="#add-location-modal">Добавить расположение</button>
                                    <button class="btn btn-default btn-block" data-toggle="modal" data-target="#add-organisation-modal">Добавить организацию</button>
                                    <button class="btn btn-default btn-block" id="add-to-button" data-toggle="modal" data-target="#add-to-modal">Запустить техническое обслуживание</button>
                                    <button class="btn btn-default btn-block" id="add-mo-button" data-toggle="modal" data-target="#add-mo-modal">Запустить метрологическое обслуживание</button>
                                </div>
                            </div>
                        </div>
                    </security:authorize>
                </div>
            </div>
        </div>
        <div class="col-md-10">
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
                                <th>Оборудование</th>
                                <th>Ответственный</th>
                                <th>Организация проводящая поверку</th>
                                <th>Период (дней)</th>
                                <th>Дата последней поверки <a href="#" id="sort-mo-date"><span class="glyphicon glyphicon-sort"></span></a></th>
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
                                    <th>Оборудование</th>
                                    <th>Ответственный</th>
                                    <th>Организация проводящая проверку</th>
                                    <th>Период (дней)</th>
                                    <th>Дата последней поверки <a href="#" id="sort-to-date"><span class="glyphicon glyphicon-sort"></span></a></th>
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
                        <h4 class="modal-title" id="to-checkout-header">Повести техническое обслуживание</h4>
                    </div>
                    <div class="modal-body">
                        <form id="do-to-checkout-form" enctype="multipart/form-data" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" id="to-checkouted-id" name="check-id" value=""/>
                            <input type="hidden" id="check-type" name="check-type"/>

                            <div class="form-group">
                                <label for="to-checkout-msg">Комментарий</label>
                                <input type="text" class="form-control" id="to-checkout-msg" name="check-msg">
                            </div>
                            <div class="form-group">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><a class="btn btn-default" id="add-file-block">Добавить файл</a></div>
                                    <div class="panel-body" id="files-block">

                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <%--<input type="submit" value="" id="to-checkout-accept" />--%>
                        <button type="button" class="btn btn-default" id="to-checkout-accept">Повести</button>
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
                                    <label for="equipment-factorynumber">Серийный номер</label>
                                    <input type="text" class="form-control" id="equipment-factorynumber">
                                </div>
                                <div class="form-group">
                                    <label for="equipment-inventorynumbe">Инвентарный номер</label>
                                    <input type="text" class="form-control" id="equipment-inventorynumbe">
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
                                    <label for="to-organisation">Организация проводящая проверку</label>
                                    <select class="form-control" id="to-organisation"></select>
                                </div>
                                <div class="form-group">
                                    <label for="to-name">Наименование</label>
                                    <input type="text" class="form-control" id="to-name">
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
                                    <label for="mo-organisation">Организация проводящая поверку</label>
                                    <select class="form-control" id="mo-organisation"></select>
                                </div>
                                <div class="form-group">
                                    <label for="mo-name">Наименование</label>
                                    <input type="text" class="form-control" id="mo-name">
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

            <%--upload histroy model--%>
            <div id="add-to-xlsx-modal" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Загрузить данные по техническому обслуживанию</h4>
                        </div>
                        <div class="modal-body">
                            <form method="post" enctype="multipart/form-data" id="add-to-xlsx">
                                <div class="form-group">
                                    <p><a href="<c:url value="/getToXlsxTemplate"/>">Шаблон</a> для заполнения</p>
                                </div>
                                <div class="form-group">
                                    <input type="file" name="file"/>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" id="add-to-xlsx-accept">Загрузить</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
                </div>
            </div>
            <%--upload histroy model--%>
            <div id="add-mo-xlsx-modal" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Загрузить данные по метрологическому обслуживанию</h4>
                        </div>
                        <div class="modal-body">
                            <form method="post" enctype="multipart/form-data" id="add-mo-xlsx">
                                <div class="form-group">
                                    <p><a href="<c:url value="/getMoXlsxTemplate"/>">Шаблон</a> для заполнения</p>
                                </div>
                                <div class="form-group">
                                    <input type="file" name="file"/>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" id="add-mo-xlsx-accept">Загрузить</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
                </div>
            </div>
            <%--add organisation modal--%>
            <div id="add-organisation-modal" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Добавить организацию</h4>
                        </div>
                        <div class="modal-body">
                            <form method="post" enctype="multipart/form-data" id="add-organisation-form">
                                <div class="form-group">
                                    <label for="organisation-name">Наименование</label>
                                    <input type="text" class="form-control" id="organisation-name">
                                </div>
                                <div class="form-group">
                                    <label for="organisation-address">Адрес</label>
                                    <input type="text" class="form-control" id="organisation-address">
                                </div>
                                <div class="form-group">
                                    <label for="organisation-code">Код(<i>Необходим для загрузки данных через XLS</i>)</label>
                                    <input type="text" class="form-control" id="organisation-code">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" id="add-organisation-accept">Добавить</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
                </div>
            </div>
        </security:authorize>
    </t:putAttribute>
</t:insertDefinition>