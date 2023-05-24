let token = document.querySelector("meta[name='_csrf']").getAttribute("content");
let header = document.querySelector("meta[name='_csrf_header']").getAttribute("content")

//Установить фокус на текстовое поле вопроса при нажатии на блок с вопросом (без этого кода при нажатии, например,
//на номер вопроса фокус на текстовое поле устанавливаться не будет)
for(let question of document.querySelectorAll(".question-entity")){
    question.addEventListener("click",(event)=>{
        let qn = parseInt(event.target.getAttribute("qnumber"));
        document.querySelector("#question"+qn+" .question-field")?.focus();
    });
}

const scroll_to = document.querySelector(".link-holder");
if(scroll_to != null) scroll_to.scrollIntoView();
