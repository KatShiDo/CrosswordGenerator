let token = document.querySelector("meta[name='_csrf']").getAttribute("content");
let header = document.querySelector("meta[name='_csrf_header']").getAttribute("content")

for(let del_crossword of document.querySelectorAll(".delete-crossword")){
    del_crossword.onclick = async function (event) {
        let status = await fetch("http://localhost:8080/crossword/delete/" + event.target.getAttribute("delete_id"),
            {
                method: "DELETE",
                headers: {
                    [header]: token,
                }
            }).then(response => response.status);
        if(status === 200){
            location.reload();
        }
        else{
            prompt("Не удалось удалить кроссворд.");
        }
        return false;
    };
}

for(let change_button of document.querySelectorAll(".user-change-button")){
    change_button.onclick = async function (event) {
        let target = event.target;
        if (target.classList.contains("change-mode")) {
            let change = target.id.slice(target.id.lastIndexOf("-") + 1);
            let value = document.getElementById(change).value;
            console.log(change + " : " + value);
            let id = target.getAttribute("user_id");
            let user = {
                [change]: value
            }
            let json = JSON.stringify(user);
            let response = await fetch("http://localhost:8080/user/" + id,
                {
                    method: "PATCH",
                    headers: {
                        [header]: token,
                        "Content-Type": "application/json"
                    },
                    body: json
                });
            if(response.status === 200) location.replace(location.href);
            else{
                let json = await response.json();
                confirm(json["error"]);
            }
        }else{
            target.setAttribute("src", "http://localhost:8080/img/check-sign.png");
            target.classList.add("change-mode");
            let textarea = document.getElementById(target.id.slice(target.id.lastIndexOf("-") + 1));
            textarea.focus();
        }
    };
}

for(let change_field of document.querySelectorAll(".changeable-field")){
    change_field.addEventListener("focusout", async (event) =>  {
        let change_button = document.getElementById("change-" + change_field.getAttribute("id"));
        change_button.setAttribute("src", "http://localhost:8080/img/pencil.png");
        change_button.classList.remove("change_mode");
    });
}


document.getElementById("file-input").onchange = async function (event) {
    let file = event.target.files[0];
    let preview = document.createElement("img");
    preview.setAttribute("src", URL.createObjectURL(file));
    document.querySelector(".big-avatar-image").remove();
    preview.classList.add("big-avatar-image");
    document.querySelector(".avatar-holder").insertBefore(preview, event.target);
    let data = new FormData()
    data.append('imageFile', file);
    await fetch("http://localhost:8080/images", {
        method: "PUT",
        headers: {
            [header]: token,
        },
        body: data
    }).then(response => Promise.all([response.status, response.json()]))
        .then(async function ([status, json]) {
            if (status === 200) {
                let user ={
                    avatar:{"id": json["id"]}
                }
                await fetch("http://localhost:8080/user/" + event.target.getAttribute("user_id"),
                    {
                        method:"PATCH",
                        headers: {
                            [header]: token,
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(user)
                    });
            }
        });
};

document.getElementById("change-password").onclick = async function (event) {
    let new_password = document.querySelector(".password-text").value;
    let result = await fetch("http://localhost:8080/user/" + event.target.getAttribute("user_id"),
        {
            method: "PATCH",
            headers: {
                [header]: token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({"password": new_password})
        });
    if(result.status !== 200){
        let json = await result.json();
        confirm(json["error"]);
    }
}