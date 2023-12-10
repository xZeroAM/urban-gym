document.addEventListener("DOMContentLoaded", function () {
  const checkboxes = document.querySelectorAll(".day_js");
  const dayTemplate = document.getElementById("template_dia");
  const dayContainer = document.querySelector(".ejercicios");
  const ejercicioTemplate = document.getElementById("form_ejercicio");

  const listaDias = [
    "Lunes",
    "Martes",
    "Miércoles",
    "Jueves",
    "Viernes",
    "Sábado",
    "Domingo",
  ];

  checkboxes.forEach(function (checkbox) {
    checkbox.addEventListener("change", () => {
      if (checkbox.checked) {
        const clonedTemplate = dayTemplate.content.cloneNode(true);
        const titulo = clonedTemplate.querySelector(".nombre_del_dia");
        const index = Array.from(checkboxes).indexOf(checkbox);
        titulo.textContent = listaDias[index];

        const ejercicio = ejercicioTemplate.content.cloneNode(true);
        const inputElement = ejercicio.querySelector(".valor_dia");
        inputElement.setAttribute("value", index + 1);

        ejercicio.appendChild(inputElement);
        clonedTemplate
          .querySelector(".dia_del_ejercicio")
          .appendChild(ejercicio);

        dayContainer.appendChild(clonedTemplate);
      } else if(!checkbox.checked) {
        const checkboxDesmarcado = checkbox.nextElementSibling.nextElementSibling.textContent
        console.log(`${checkboxDesmarcado} checkboxDesmarcado`)
        const x = document.querySelectorAll('.dia_del_ejercicio')
        for(let i=0; i<x.length; i++) {
          console.log(x[i].querySelector('.nombre_del_dia').textContent)
          if(x[i].querySelector('.nombre_del_dia').textContent == checkboxDesmarcado) {
            dayContainer.removeChild(x[i])
          }
        }
      }
    });
  });

  // Evento delegado para el botón de agregar ejercicio
  dayContainer.addEventListener("click", function (event) {
    if (event.target.classList.contains("btn_agregar")) {
      const dayElement = event.target.closest(".dia_del_ejercicio");
      const ejercicio = ejercicioTemplate.content.cloneNode(true);

      let valorDia = 0;

      for (let i = 0; i < listaDias.length; i++) {
        if (
          dayElement.querySelector(".nombre_del_dia").textContent ==
          listaDias[i]
        ) {
          valorDia = i + 1;
          console.log(valorDia);
        }
      }

      const inputElement = ejercicio.querySelector(".valor_dia");
      inputElement.setAttribute("value", valorDia);

      dayElement.appendChild(ejercicio);
    } else if (event.target.classList.contains("btn_quitar")) {
      const dayElement = event.target.closest(".dia_del_ejercicio");
      if (dayElement.childElementCount > 4) {
        dayElement.removeChild(dayElement.lastElementChild);
      } else {
      }
    }
  });
});
