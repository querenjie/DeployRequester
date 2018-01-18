;(function($){
    //拖动
    var Drag = function Drag(table){
      var ochek=document.getElementById("chenkbox"),
          otable=document.getElementById(table+'-1'),
          otable2=$('#'+table+'-2'),
          otody=otable.tBodies[0],
          oth=$("#"+table+"-2 th"),
          otd=$("#"+table+"-1 td"),
          otd2=$("#"+table+"-2 td"),
          box=document.getElementById("box"),
          tdlen = $("#"+table+"-1 tr:eq(1)").find('td').length,
          arrn=[];
          console.log($("#"+table+"-1 tr:eq(1)").find('td').html());

          for (var i = 0; i < otd.length; i++) {
            if (i <=1 || i % tdlen == 0) {
              continue;
            };
            otd[i].onmousedown=function(e){
                var e=e||window.event,
                    target = e.target||e.srcElement,
                    thW = target.offsetWidth,
                    maxl=ochek.offsetWidth-thW,
                    rows=otable.rows,
                    rows2=otable2.rows,
                    ckL=ochek.offsetLeft,
                    disX=target.offsetLeft,
                    _this=this,
                    cdisX=e.clientX-ckL-disX;
                    var html = $('#'+table+'-2 th:eq('+this.cellIndex+')').html();
                    $('#box').append('<p class="'+table+'-2">'+html+'</p>');  
                    for (var i = 0; i < rows.length; i++) {
                        var op=document.createElement("p");
                        html=rows[i].cells[this.cellIndex].innerHTML;  
                        $('#box').append('<p class="'+table+'-1">'+html+'</p>');  
                    };    
                    for (var i = 0; i < oth.length; i++) {
                           arrn.push(oth[i].offsetLeft);      
                    }; 
                    console.log(arrn);
                    box.style.display="block";
                    box.style.width=thW+"px";
                    box.style.left=disX+"px";
                    //未完成 还有事件没写。
                    document.onmousemove=function(e){
                        var e=e||window.event,
                        target = e.target||e.srcElement,
                        thW = target.offsetWidth;
                        box.style.top=0;
                        box.style.left=e.clientX-ckL-cdisX+"px";
                        if(box.offsetLeft>maxl){
                             box.style.left=maxl+"px";
                        }else if(box.offsetLeft<0){
                             box.style.left=0;
                        }        
                        document.onselectstart=function(){return false};     
                      window.getSelection ? window.getSelection().removeAllRanges() : doc.selection.empty();              
                    }
                    document.onmouseup=function(e){
                      var e=e||window.event,
                          opr=box.getElementsByTagName("p"),
                          oboxl=box.offsetLeft+cdisX;
                          for (var i = 0; i < arrn.length; i++) {
                             if(arrn[i]<oboxl){
                              var index=i;
                             }
                          };
                          for (var i = 0; i < rows.length; i++) {
                            rows[i].cells[_this.cellIndex].innerHTML="";
                            rows[i].cells[_this.cellIndex].innerHTML=rows[i].cells[index].innerHTML;
                            rows[i].cells[index].innerHTML="";
                            rows[i].cells[index].innerHTML=$('#box .'+table+'-1:eq('+i+')').html();
                          };
                         $('#'+table+'-2 th:eq('+_this.cellIndex+')').html($('#'+table+'-2 th:eq('+index+')').html());
                         $('#'+table+'-2 th:eq('+index+')').html($('#box .'+table+'-2').html());
                         console.log($('#box .'+table+'-2').html());
                         console.log(index);
                       
                      var len = $("#sample-table-1 .fixedHeader tr th").length || 0;
                          if (len > 0) {
                            for(var i = 0; i < len; i++){
                              $("#sample-table-1 .fixedHeader tr th:eq("+i+")").width($("#sample-table-1 .scrollContent tr:eq("+i+") td:eq("+i+")").width());
                            }
                          };
                         box.innerHTML="";
                         arrn.splice(0,arrn.length);
                         box.style.display="none";
                         document.onmousemove=null; 
                         document.onmouseup=null;
                         document.onselectstart=function(){return false};     
                    }

               }
          };
          
    };

  $(document).ready(function(){
    Drag("sample-table");
  });
}(jQuery));




