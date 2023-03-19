//Установить для сетки кроссворда соотношение сторон 1 к 1 (необходимо, если не использовать относ. величины, 
//такие как em и vw, при задании размера сетки)
/*let ch = document.querySelector(".crossword-holder").offsetHeight;
document.querySelector(".crossword-holder").style.cssText += "width: "+ch + "px";*/

//Создать сетку 20 на 20
let x = 20
let y = 20
let crossword_grid = document.querySelector(".crossword-holder");
for(let i = 0;i < x;i++){
    for(let k = 0; k < y;k++){
        let div = document.createElement("div");
        div.setAttribute("class", "grid-cell");
        crossword_grid.appendChild(div);
    }
}

//Установить фокус на текстовое поле вопроса при нажатии на блок с вопросом (без этого кода при нажатии, например,
//на номер вопроса фокус на текстовое поле устанавливаться не будет)
for(let question of document.querySelectorAll(".question-entity")){
    question.addEventListener("click",(event)=>{
        let qn = parseInt(event.target.getAttribute("qnumber"));
        document.querySelector("#question"+qn+" .question-field").focus();
    });
}

//Показать поле входа в аккаунт
document.getElementById("sign-in").addEventListener("click", ()=>{
    let signin = document.querySelector(".signin-form");
    signin.style.top="20%";
});

//Скрыть поле входа в аккаунт
document.getElementById("close-signin-form").addEventListener("click", ()=>{
    let signin = document.querySelector(".signin-form");
    signin.style.top="-100%";
});

//Показать поле регистрации
document.getElementById("sign-up").addEventListener("click", ()=>{
    let signin = document.querySelector(".signup-form");
    signin.style.top="20%";
});

//Скрыть поле регистрации
document.getElementById("close-signup-form").addEventListener("click", ()=>{
    let signin = document.querySelector(".signup-form");
    signin.style.top="-100%";
});
