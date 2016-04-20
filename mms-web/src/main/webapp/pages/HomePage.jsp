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

            var requestObject = {${_csrf.parameterName}:"${_csrf.token}"};

            function updateDashBoard(start,finish){
                requestObject.start=start;
                requestObject.finish=finish;
                $.ajax({
                    url:'getDashboard',
                    data:requestObject,
                    type:'post',
                    success:function(data){

                    }
                });
            };

            $(document).ready(function(){
                $('#startPeriod').datetimepicker({
                    format: 'DD.MM.YYYY'
                });
                $('#finishPeriod').datetimepicker({
                    format: 'DD.MM.YYYY'
                });
                $("#startPeriod").on("dp.change", function (e) {
                    $('#finishPeriod').data("DateTimePicker").minDate(e.date);
                });
                $("#finishPeriod").on("dp.change", function (e) {
                    $('#startPeriod').data("DateTimePicker").maxDate(e.date);
                });

            });
        </script>
    </t:putAttribute>
    <t:putAttribute name="content">
        <div class="row">
            С
            <div class="form-group">
                <div class='input-group date' id='startPeriod'>
                    <input type='text' class="form-control" />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar">
                    </span>
                </span>
                </div>
            </div>
        <div class="form-group">
             По
            <div class='input-group date' id="finishPeriod">
                <input type='text' class="form-control" />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar">
                    </span>
                </span>
            </div>
        </div>
        <div class="row">
            <div id="dashContainer">

            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>