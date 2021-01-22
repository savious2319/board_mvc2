/**
 * 회원가입
 */

//var cnt = 0;
var check = false;

function formSubmit(){
	var form = document.joinForm;
	
	if(form.member_id.value == "" || !check){
		alert("아이디를 확인해주세요.");
		form.member_id.focus();
		return false;
	}
	
/*	if(cnt == 2){
		cnt = 0;
		//alert(cnt);
		alert("중복된 아이디 입니다. 다른 아이디를 입력해주세요");
		
		frm.member_id.focus();
		return false;
	}
	
	if(cnt == 0){
		alert("아이디 중복체크를 해주세요");
		frm.check.focus();
		return false;
	}*/
	
	form.submit();
	
}


//jQuery로 ajax 사용하기
function checkId(id){
	check = false;
	if(id == ""){
		$("#idCheck_text").text("아이디를 작성해주세요.");
	}else{
		$.ajax({
			url: contextPath + "/member/CheckId.me?member_id="+id,
			type: 'get',
			dataType: 'text',
			success: function(data){
				if(data.trim() == "ok"){
					check = true;
					$("#idCheck_text").text("사용할 수 있는 아이디입니다.");
				}else{
					$("#idCheck_text").text("중복된 아이디입니다.");
				}
			},
			error: function(){
				console.log("오류");
			}
		})
	}
}
$("input[name='member_id']").keyup(function(event){
	var id = $("input[name='member_id']").val();
	checkId(id);
});

/*function checkId(id){
	cnt++;
	
	if(id == ""){
		alert("아이디를 입력해주세요.");
		return false;
	}
	//Ajax
	//결과를 DOM을 통해서 태그에 메세지 추가
	var httpRequest = new XMLHttpRequest();
	httpRequest.open("GET", "/board_mvc2/member/CheckIdAction.me?member_id="+ id, true);
	httpRequest.send();
	
	httpRequest.onreadystatechange = function(){
		if(httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200){
			//httpRequest.responseText : ok인지 not-ok인지
			//alert(httpRequest.responseText.trim());
			if(httpRequest.responseText.trim() == "ok"){
				//document.getElementById("checkId_text").innerHTML = "사용할 수 있는 아이디 입니다.";
				alert("사용할 수 있는 아이디 입니다.");
			}else{
				cnt++;
				//document.getElementById("checkId_text").innerHTML = "중복된 아이디 입니다.";
				alert("중복된 아이디 입니다.");
			}
		}
	}
}*/








