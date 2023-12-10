addEventListener('DOMContentLoaded', () => {
    const btn_menu = document.querySelector('.boton-nav')
    if(btn_menu) {
        btn_menu.addEventListener('click', () => {
            const menu_items = document.querySelector('.navegador-items')
            menu_items.classList.toggle('activar-nav')
        })
    }
})


window.addEventListener("scroll", function() {
    var navegador = document.querySelector(".navegador")
    navegador.classList.toggle("sticky-nav", window.scrollY > 0)
})