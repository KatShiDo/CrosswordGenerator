let token = document.querySelector("meta[name='_csrf']").getAttribute("content");
let header = document.querySelector("meta[name='_csrf_header']").getAttribute("content")

let current_word = -1, current_char = -1;

for(let st of document.querySelectorAll(".solution-text")){
    st.onkeydown = function (event){
        let target = event.target;
        let word;
        let char;
        if(target.classList.length === 2){
            let id = target.classList[1];
            word = parseInt(id.slice(4, id.lastIndexOf("-")));
            char = parseInt(id.slice(id.lastIndexOf("-") + 5));
            current_word = word;
            current_char = char;
        }
        else{
            let w1_s = target.classList[1], w2_s = target.classList[2];
            let w1 = parseInt(w1_s.slice(4, w1_s.lastIndexOf("-"))),
                w2 = parseInt(w2_s.slice(4, w2_s.lastIndexOf("-")));
            if(w1 !== current_word && w2 !== current_word){
                word = w1;
                current_word = word;
                let id = target.classList[1];
                char = parseInt(id.slice(id.lastIndexOf("-") + 5));
                current_char = char;
            }
            else{
                word = current_word;
                char = current_char;
            }

        }
        console.log("handling textarea event " + event.key);
        if(event.key !== "Backspace" && event.key !== "ArrowLeft" && event.key !== "ArrowRight"){
            console.log("Add char, word " + word + " char " + char);
            if(target.value === "") target.value = event.key;
            let nextchar = document.querySelector(".word" + word + "-char"+(char + 1));
            let i = 2;
            while(nextchar !== null && nextchar.value !== ""){
                nextchar = document.querySelector(".word" + word + "-char"+(char + i));
                i += 1;
            }
            if(nextchar != null){
                nextchar.focus();
                current_char++;
            }else{
                current_word = -1;
            }
        }else if(event.key === "Backspace" || event.key === "ArrowLeft") {
            console.log("Move from char " + char + " to char " + (char - 1) + " word " + word);
            if(event.key === "Backspace") target.value = "";
            let nextchar = document.querySelector(".word" + word + "-char" + (char - 1));
            if (nextchar != null) {
                nextchar.focus();
                current_char--;
            } else {
                current_word = -1;
                current_char = -1;
            }
        }
        else if(event.key === "ArrowRight"){
            console.log("Move from char " + char + " to char " + (char + 1) + " word " + word);
            let nextchar = document.querySelector(".word" + word + "-char"+(char + 1));
            if(nextchar !== null){
                nextchar.focus();
                current_char++;
            }
            else{
                current_word = -1;
                current_char = -1;
            }
        }
        return false;
    };
}

document.getElementById("check-button").addEventListener("click", async (event) => {
    let words = [];
    let word = 0, char = 0;
    let current_word = ""
    while (true) {
        let char_in_word = document.querySelector(".word" + word + "-char" + char);
        if (char_in_word === null && char === 0) {
            break;
        } else if (char_in_word === null){
            char = 0;
            words.push(current_word);
            current_word = "";
            word++;
        } else {
            if (char_in_word.value !== "")
                current_word += char_in_word.value;
            else
                current_word += " ";
            char++;
        }
    }
    let json = JSON.stringify({"words": words});
    let response = await fetch(location.href,
        {
            method: "POST",
            headers: {
                [header]: token,
                "Content-Type": "application/json"
            },
            body: json
        });
    if(response.status === 200){
        let json = await response.json();
        let accuracy = json["accuracy"];
        let accuracy_holder = document.getElementById("accuracy");
        let words_solved = document.getElementById("words-count");
        accuracy_holder.innerHTML=accuracy + "%";
        words_solved.innerHTML = json["wordsSolved"];
        let word_i = 0;
        for(let mistakes_in_word of json["mistakes"]){
            for(let mistake_pos of mistakes_in_word){
                let char_in_word = document.querySelector(".word" + word_i + "-char" + mistake_pos);
                char_in_word.style.backgroundColor="#F55555";
            }
            word_i++;
        }
        for(let result of document.querySelectorAll(".results")){
            result.style.display="block";
        }
    }
});

