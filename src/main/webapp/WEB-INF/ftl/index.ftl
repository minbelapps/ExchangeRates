<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Exchange rates</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>  
  
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"  crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/css/bootstrap-datepicker3.min.css"  crossorigin="anonymous">
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"  crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"  crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js" ></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/css/bootstrap-select.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/js/bootstrap-select.min.js"></script>
</head
<body>
<style>
	.spleft{
		padding-left: 14px;
	}
	.date-input_cont{
		background-color:rgb(300, 255, 221);
	}
	.container{
	  	background-color:rgb(236, 239, 221);
	}
	.up{
		background: url("http://www.lb.lt/img/icons/change_up.gif") 0 center no-repeat;
	}
	.down{
		background: url("http://www.lb.lt/img/icons/change_down.gif") 0 center no-repeat;
	}
</style>

<#assign descriptonMap =  model["currenciesDescrptions"]>
 
<div id="content">
   <div id="header">
		<H2>
		    Exchange rates
		</H2>
	</div>
				
	
    <div class="text-center date-input_cont"  action="changeDate.html" method="post">
    	<#if model["hardcoded"] == true>
			<input name="date" value="${model["date"]}" disabled="disabled"> <br/> </br>
			<select class="selectpicker" data-live-search="true" >
				<option value="_all_">All currencies</option>
				<#list descriptonMap?keys as key>
    			     <option value="${key}">${key}</option>
				</#list>

			</select>
		<#else>
	     	<form name="changeDate" id="changeDateForm">
	   			<input name="date" value="${model["date"]}" class="date-input">
	   			<button type="button" id="submitButton" class="btn btn-primary btn-m">Submit</button>
	   		</form>
    	
    	</#if>	
    </div>	
  <br/>

  </table>
    <table id ="currencyTable" class="datatable container">
    <tr>
        <th></th>
        <th>Currency name</th>  
        <th>Currency</th>
        <th>CER (- 1 day) </th>
        <th>CER</th>
        <th>DIFF </th>
        <th>DIFF %</th>
    </tr>
    
    <#list model["exchangeRates"].exchangeRates as rate>
	    <#assign description = "-">
	    <#assign classratio = "">
	    <#if (descriptonMap[rate.currency])??>    	
    		<#assign description = descriptonMap[rate.currency].description>
    	</#if>	
	    <#if rate.diff gt 0>
	    	 <#assign classratio = "up">
	    <#elseif rate.diff lt 0 >
	    	 <#assign classratio = "down">
	    </#if>	    	
	    <tr id="${rate.currency}">
	        <td><img src="http://www.lb.lt/img/Flags/${rate.currency}.gif"  width="30" height="19" style="border:1px solid #E6E6E6;"></td>
	        <td>${description}</td> 
	        <td>${rate.currency}</td> 
	        <td>${rate.value1}</td>
	        <td>${rate.value2}</td>
	        <td>${rate.diff}</td>
	        <td class="spleft ${classratio}">${rate.diffRatio}%</td>
	    </tr>
    </#list>
  </table>
</div>  
</body>
</html>  
<script>

$(function(){
	$(".date-input").datepicker({
	    format: 'yyyy-mm-dd',
	    endDate: '+0d',
	    autoclose: true  
	}) .on('changeDate', function (ev) {
   		 //var date=ev.format();
   		 //$("#changeDateForm").submit();   		 
	});
	$('.selectpicker').selectpicker({
	   style: 'btn-info'
	});
	$('.selectpicker').on("change", function (ev) {
   		filter($(this).find("option:selected").val());
	});
	$("#submitButton").on("click", function (ev) {
   		$("#changeDateForm").submit();   	
	});
	function filter(value){
		if(value=='_all_'){
			$("#currencyTable tr").show();
		}else{
			$("#currencyTable tr").hide();
			$("#currencyTable #"+value).show();
		}
	}
});

	
</script>