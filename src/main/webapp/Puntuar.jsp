<%@ page import="Controller.GestionAPP" %>
<%@ page import="Modelos.Trato" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 23/07/2026
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
        Trato trato = gestionAPP.buscarTratoId(Integer.parseInt(request.getParameter("id")));
        session.setAttribute("idTrato",request.getParameter("id"));
    %>
    <title><%="Puntuando " + trato.getProducto().getTitulo()%></title>
    <link rel="stylesheet" type="text/css" href="CSS/Puntuar.css?v=2">
    <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
</head>
<body>
<%
    // Paleta de colores estilo Google
    String[] coloresAvatar = {
            "#e53935", "#8e24aa", "#3949ab", "#1e88e5",
            "#00897b", "#43a047", "#fb8c00", "#f4511e",
            "#6d4c41", "#546e7a", "#d81b60", "#00acc1",
            "#fdd835","#c0ca33","#7cb342","#1565c0","#5e35b1",
            "#ec407a","#ffb300","#2e7d32"
    };

    out.print("<div id=\"bannerArriba\">");
    out.print("<img alt=\"logo\" src=\"imagenes/logo%20fernanpop.png\">");

    out.print("<div class=\"infoUsuario\">");
    if (gestionAPP.getUsuario() != null) {
        String nombre = gestionAPP.getUsuario().getNombre();
        String apel   = gestionAPP.getUsuario().getApel();
        String nombreCompleto = nombre + " " + apel;
        String email = gestionAPP.getUsuario().getEmail();

        // Color consistente según el nombre (como Google)
        int hash = Math.abs(email.hashCode());
        String colorAvatar = coloresAvatar[hash % coloresAvatar.length];

        // Inicial para el avatar
        String inicial = !email.isEmpty() ? email.substring(0, 1).toUpperCase() : "?";

        out.print("<div class=\"avatarUsuario\" style=\"background-color:" + colorAvatar + "\">" + inicial + "</div>");
        out.print("<span class=\"nombreUsuario\">" + nombreCompleto + "</span>");
    } else {
        out.print("<div class=\"avatarUsuario avatarSinSesion\">?</div>");
        out.print("<span class=\"sinSesion\">Sesión no iniciada</span>");
    }
    out.print("</div>");

    out.print("<button onclick=\"window.location.href = 'ValidaSesion.jsp'\"><svg viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "                <circle cx=\"12\" cy=\"8.5\" r=\"3.6\" fill=\"#ffffff\"/>\n" +
            "                <path d=\"M4.8 19.2c1.2-3.4 4-5.1 7.2-5.1s6 1.7 7.2 5.1c.25.7-.25 1.4-1 1.4H5.8c-.75 0-1.25-.7-1-1.4z\" fill=\"#ffffff\"/>\n" +
            "            </svg> Perfil</button>");
    out.print("</div>");
%>
<div id="formulario">
<form action="GestionaValoracion.jsp" method="get">
    <div class="contenedor">
        <div class="estrellas" id="estrellas"></div>
        <input type="hidden" id="puntuacion" name="puntuacion" value="0">
    </div>
    <input type="text" name="comentario" placeholder="comentario">
    <input type="submit" value="valorar">

    <script>
        const contenedorEstrellas = document.getElementById('estrellas');
        const inputPuntuacion = document.getElementById('puntuacion');

        // Puntos de una estrella de 5 picos en un viewBox de 100x100
        const puntosEstrella = "50,5 61,38 96,38 68,59 79,92 50,71 21,92 32,59 4,38 39,38";

        let puntuacionActual = 0;

        // Crear las 5 estrellas (SVG) una sola vez
        for (let i = 0; i < 5; i++) {
            const svgNS = "http://www.w3.org/2000/svg";
            const svg = document.createElementNS(svgNS, "svg");
            svg.setAttribute("viewBox", "0 0 100 100");

            const polygon = document.createElementNS(svgNS, "polygon");
            polygon.setAttribute("points", puntosEstrella);
            polygon.dataset.index = i;

            svg.appendChild(polygon);
            contenedorEstrellas.appendChild(svg);

            // Al hacer click en una estrella, se marca la puntuación hasta esa posición
            svg.addEventListener('click', () => {
                const valorClicado = i + 1;
                // Si se pulsa la misma estrella que ya estaba marcada como última, se pone a 0
                puntuacionActual = (puntuacionActual === valorClicado) ? 0 : valorClicado;
                actualizarEstrellas(puntuacionActual);
            });
        }

        function actualizarEstrellas(valor) {
            const poligonos = contenedorEstrellas.querySelectorAll('polygon');
            poligonos.forEach((polygon) => {
                const indice = parseInt(polygon.dataset.index);
                if (indice < valor) {
                    polygon.classList.add('rellena');
                } else {
                    polygon.classList.remove('rellena');
                }
            });

            inputPuntuacion.value = valor;
        }

        // Estado inicial
        actualizarEstrellas(puntuacionActual);
    </script>
</form>
</div>
<!-- Menú de abajo -->
<div id="menu">
    <button onclick="window.location.href = 'BorraVariablesBuscar.jsp'">
        <svg class="icono-menu" viewBox="0 0 24 24" fill="none" stroke="currentColor"
             stroke-width="1.75" stroke-linecap="round" stroke-linejoin="round">
            <path d="M3.5 10.5L12 3.5l8.5 7"/>
            <path d="M5.5 9.5V20a1 1 0 0 0 1 1h3.5v-6h4v6H17.5a1 1 0 0 0 1-1V9.5"/>
        </svg>
        <p>Inicio</p>
    </button>
    <button onclick="window.location.href = 'Compras.jsp'">
        <svg class="icono-menu" viewBox="0 0 499.879252 322.847085" xmlns="http://www.w3.org/2000/svg">
            <g transform="translate(-2.092491,329.510977) scale(0.100000,-0.100000)" fill="none" stroke="currentColor" stroke-width="250" stroke-linejoin="round" stroke-linecap="round">
                <path d="M777 3288 c-9 -7 -66 -116 -128 -243 -61 -126 -144 -297 -184 -380
-40 -82 -115 -238 -168 -345 -300 -618 -291 -595 -263 -622 7 -7 131 -71 274
-142 295 -146 294 -146 329 -60 21 50 30 53 70 21 15 -13 96 -79 178 -147 83
-68 151 -125 153 -126 2 -1 -8 -25 -22 -53 -74 -146 15 -324 172 -346 l50 -7
6 -51 c20 -149 145 -252 282 -231 40 6 44 5 44 -15 0 -107 141 -219 261 -208
l57 5 11 -42 c16 -63 62 -120 122 -150 103 -51 211 -25 302 73 26 28 52 51 57
51 5 0 42 -27 83 -59 171 -136 218 -156 317 -139 95 17 184 104 199 196 6 32
10 37 31 34 113 -16 148 -13 198 12 80 40 125 102 147 199 4 22 10 27 23 21 9
-4 45 -9 78 -12 133 -10 242 81 262 220 l7 53 60 6 c79 8 112 22 158 67 45 43
60 69 76 134 12 52 0 136 -25 173 -8 11 -14 25 -14 32 0 14 372 326 394 331 8
2 17 -7 21 -20 11 -35 54 -88 71 -88 20 0 521 248 540 267 28 28 37 6 -208
513 -44 91 -111 228 -148 305 -332 686 -372 765 -394 777 -15 8 -68 -15 -280
-122 -143 -72 -269 -140 -278 -152 -22 -24 -17 -51 25 -131 33 -65 35 -60 -38
-99 -153 -81 -206 -78 -569 26 -293 85 -413 88 -575 15 -73 -33 -83 -34 -140
-14 -126 44 -280 39 -491 -16 -222 -59 -272 -69 -339 -69 -70 0 -144 20 -216
58 -71 37 -69 31 -29 115 55 117 66 104 -234 255 -289 146 -292 147 -315 130z
m3593 -438 c79 -162 202 -414 273 -560 212 -435 254 -525 250 -533 -6 -10
-379 -197 -393 -197 -5 0 -47 78 -94 173 -46 94 -152 312 -236 482 -262 533
-360 736 -360 746 0 18 394 208 406 196 6 -7 75 -145 154 -307z m-3330 215
c105 -52 190 -100 190 -106 0 -11 -46 -108 -230 -484 -471 -962 -448 -917
-470 -910 -60 19 -380 187 -380 199 0 8 63 144 141 303 78 158 163 333 189
388 26 55 103 213 170 350 67 138 133 274 146 303 14 28 31 52 39 52 9 0 101
-43 205 -95z m2035 -349 c273 -80 314 -90 405 -90 92 0 176 22 259 69 23 14
49 25 55 25 7 0 57 -91 111 -202 54 -112 172 -353 262 -537 l164 -333 -38 -33
c-151 -128 -397 -325 -405 -323 -4 2 -136 114 -291 250 -750 654 -874 762
-895 775 -20 14 -38 12 -207 -31 -102 -25 -194 -46 -205 -46 -11 0 -39 -21
-62 -47 -138 -150 -203 -196 -316 -223 -68 -16 -202 4 -202 31 0 7 133 194
298 418 60 82 17 57 422 244 232 107 231 107 370 103 102 -3 134 -9 275 -50z
m-786 15 l24 -6 -29 -14 c-16 -8 -83 -38 -149 -68 -140 -64 -145 -67 -238
-198 -40 -55 -121 -167 -182 -250 -103 -142 -110 -153 -110 -198 0 -61 35 -97
118 -121 180 -52 356 11 527 189 l64 68 141 34 c77 19 155 39 173 43 37 9 1
37 357 -275 82 -71 167 -146 189 -165 50 -43 402 -348 512 -444 44 -39 110
-97 147 -129 102 -90 115 -174 37 -248 -81 -77 -134 -64 -306 78 -455 376
-535 438 -566 438 -36 0 -53 -22 -45 -56 4 -17 98 -99 285 -251 393 -320 365
-293 370 -364 5 -73 -7 -104 -52 -139 -28 -21 -47 -27 -87 -27 -59 0 -43 -11
-331 214 -116 90 -246 191 -291 226 -84 65 -105 72 -132 46 -41 -42 -11 -74
255 -274 122 -92 244 -196 261 -223 62 -103 -37 -244 -157 -221 -19 3 -100 60
-210 145 -151 118 -184 139 -209 137 -22 -2 -32 -10 -39 -30 -12 -35 -2 -46
131 -149 116 -90 137 -120 131 -189 -9 -88 -89 -151 -172 -136 -29 6 -154 94
-248 175 -4 4 1 30 12 59 24 63 25 92 4 161 -20 69 -84 135 -150 154 l-48 14
3 60 c4 90 -15 140 -74 198 -49 49 -104 75 -160 75 -23 0 -25 4 -25 45 0 186
-200 299 -364 206 -59 -33 -75 -31 -76 7 0 7 -21 35 -46 62 -104 112 -241 106
-365 -17 l-57 -55 -193 160 c-159 130 -194 164 -192 183 1 13 98 220 215 459
118 239 232 472 253 518 22 45 45 82 52 82 6 0 43 -16 80 -35 172 -88 240 -87
623 13 63 17 126 33 140 35 39 7 173 6 199 -2z m-845 -1402 c39 -34 52 -77 42
-134 -5 -27 -31 -60 -109 -140 -100 -103 -103 -105 -147 -105 -87 0 -147 63
-137 146 4 36 16 56 65 112 115 129 145 152 201 152 42 0 56 -5 85 -31z m407
-102 c70 -47 86 -133 37 -202 -13 -18 -90 -103 -172 -189 -163 -171 -188 -187
-264 -166 -81 21 -129 126 -89 196 23 41 291 323 344 363 38 28 100 27 144 -2z
m284 -307 c42 -40 54 -99 32 -151 -17 -41 -257 -301 -293 -317 -13 -7 -45 -12
-69 -12 -92 0 -165 114 -124 193 24 46 257 292 297 314 45 24 117 12 157 -27z
m162 -301 c66 -25 101 -93 83 -160 -15 -54 -174 -218 -220 -227 -57 -12 -89
-3 -125 34 -31 31 -35 40 -35 89 0 49 4 60 37 97 145 167 188 195 260 167z"/>
            </g>
        </svg>
        <p>Tratos</p>
    </button>

    <button onclick="window.location.href = 'CrearProducto.jsp'"></button>

    <button onclick="window.location.href = 'SeleccionChats.jsp'">
        <svg class="icono-menu" viewBox="0 0 24 24" fill="none" stroke="currentColor"
             stroke-width="1.75" stroke-linecap="round" stroke-linejoin="round">
            <path d="M12 3.5c-5 0-8.5 3.2-8.5 7.2 0 2.4 1.3 4.5 3.4 5.9L5.8 20l4-1.6c.7.15 1.4.2 2.2.2 5 0 8.5-3.2 8.5-7.2S17 3.5 12 3.5z"/>
            <circle cx="8.3" cy="10.7" r="0.9" fill="currentColor" stroke="none"/>
            <circle cx="12" cy="10.7" r="0.9" fill="currentColor" stroke="none"/>
            <circle cx="15.7" cy="10.7" r="0.9" fill="currentColor" stroke="none"/>
        </svg>
        <p>Chats</p>
    </button>

    <button onclick="window.location.href = 'ChatBot.jsp'">
        <svg class="icono-menu" viewBox="0 0 24 24" fill="none" stroke="currentColor"
             stroke-width="1.75" stroke-linecap="round" stroke-linejoin="round">
            <line x1="12" y1="2.5" x2="12" y2="5"/>
            <circle cx="12" cy="2" r="0.9" fill="currentColor" stroke="none"/>
            <path d="M6 8.5c0-2 2.7-3.5 6-3.5s6 1.5 6 3.5v6c0 3-2.7 5-6 5s-6-2-6-5z"/>
            <ellipse cx="9.7" cy="11.5" rx="1" ry="1.4" fill="currentColor" stroke="none"/>
            <ellipse cx="14.3" cy="11.5" rx="1" ry="1.4" fill="currentColor" stroke="none"/>
        </svg>
        <p>ChatBot</p>
    </button>
</div>
</body>
</html>
