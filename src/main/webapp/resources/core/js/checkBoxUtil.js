

//反选
function reverseCheck(name)
{
  var eles=document.getElementsByName(name);  
  for (var i=0;i<eles.length;i++)
   {
		var e = eles[i];		
		e.checked=!e.checked;   
  }	
}
   
//全不选
function unCheckAll(name)
{
  var eles=document.getElementsByName(name);  
  for (var i=0;i<eles.length;i++)
   {
		var e = eles[i];
		e.checked=false;   
  }
}
   
//全选
function checkAll(name)
{
  var eles=document.getElementsByName(name);  
  for (var i=0;i<eles.length;i++)
   {
		var e = eles[i];
		e.checked=true;   
  }
}

//获得已经选中的checkBox,返回对象数组
function getCheckBoxs(name){
	var count=0;
	var checkedArray = new Array();
	var checkBoxs=document.getElementsByName(name);  
	for (var i=0;i<checkBoxs.length;i++)
	{
		var e = checkBoxs[i];
		if(e.checked){
			checkedArray[count++]=checkBoxs[i];
		}
	}
	return checkedArray;
}

//获得已经选中的checkBox,返回对象数组
function ifChecked(name){	
	var checkBoxs=document.getElementsByName(name);  
	for (var i=0;i<checkBoxs.length;i++)
	{
		var e = checkBoxs[i];
		if(e.checked){
			return true;
		}
	}
	return false;
}


//获得未选中的checkBox,返回对象数组
function getUnCheckBoxs(name){
	var count=0;
	var uncheckedArray = new Array();
	var checkBoxs=document.getElementsByName(name);  
	for (var i=0;i<checkBoxs.length;i++)
	{
		var e = checkBoxs[i];
		if(!e.checked){
			uncheckedArray[count++]=checkBoxs[i];
		}
	}
	return uncheckedArray;
}


//设置选中值
function checkBoxs(name,matchFunction){
	if(!matchFunction&&typeof(matchFunction)!="function"){
		alert('没有实现匹配方法!');
		return;
	}
	var checkBoxs=document.getElementsByName(name);  
	for (var i=0;i<checkBoxs.length;i++)
	{
		var e = checkBoxs[i];
		if(matchFunction(checkBoxs[i])){
			checkBoxs[i].checked=true;
		}
	}
}

//设置复选框状态
function setCheckBoxs(name,matchFunction){
	if(!matchFunction&&typeof(matchFunction)!="function"){
		alert('没有实现匹配方法!');
		return;
	}
	var checkBoxs=document.getElementsByName(name);  
	for (var i=0;i<checkBoxs.length;i++)
	{
		var e = checkBoxs[i];
		if(matchFunction(checkBoxs[i])){
			checkBoxs[i].disabled=true;
		}
	}
}

//获得已经选中的checkBox,返回以两个指定列的值组成的二维数组
function getCheckBoxsMap(name, value){
	
	var checkedArray = new Array();
	var checkBoxs=document.getElementsByName(name);  
	var checkValues=document.getElementsByName(value);
	
	var count=0;
	//生成第一维，必须先声明第一维，然后才可以声明第二维，声明时，用checkedArray[count++]失败
	for (var i=0;i<checkBoxs.length;i++){
		var e = checkBoxs[i];
		if(e.checked){
			checkedArray[count] = new Array();
			checkedArray[count][0]=checkBoxs[i].value;
			checkedArray[count][1]=checkValues[i].value;
			count ++;
		}
	}
	//for (var k=0;k<checkedArray.length;k++){
	//	alert(checkedArray[k][0] + " --- " + checkedArray[k][1]);
	//}
	return checkedArray;
}


//隐藏复选框
function hideCheckBoxs(name,matchFunction){
	if(!matchFunction&&typeof(matchFunction)!="function"){
		alert('没有实现匹配方法!');
		return;
	}
	var checkBoxs=document.getElementsByName(name);  
	var objAry = new Array();
	for (var i=0;i<checkBoxs.length;i++)
	{
		if(matchFunction(checkBoxs[i])){
			objAry[objAry.length] = checkBoxs[i];
		}
	}
	for (var j=0;j<objAry.length;j++)
	{
		objAry[j].outerHTML = "";
	}	
}