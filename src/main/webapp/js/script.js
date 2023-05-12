const radioBtn = document.querySelectorAll("input[type='radio']");
const checkBtn = document.querySelectorAll("input[type='checkbox']");

const radioAchat = document.getElementById("radio-achat");
const radioVente = document.getElementById("radio-vente");

const enchereOuverte = document.getElementById("enchere-ouverte");
const mesEncheres = document.getElementById("mes-encheres");
const enchereRemportees = document.getElementById("enchere-remportees");

const venteCours = document.getElementById("vente-cours");
const venteDebutees = document.getElementById("vente-debutees");
const venteTerminees = document.getElementById("vente-terminees");


radioAchat.addEventListener("change", () => {
    if (radioAchat.checked) {
        radioBtn.forEach(radio => radio.disabled = false);
        checkBtn.forEach(check => check.disabled = false);
        checkBtn.forEach(check => check.checked = false);
        enchereOuverte.checked = true;
        venteCours.disabled = true;
        venteDebutees.disabled = true;
        venteTerminees.disabled = true;
    }
});

radioVente.addEventListener("change", () => {
    if (radioVente.checked) {
        radioBtn.forEach(radio => radio.disabled = false);
        checkBtn.forEach(check => check.disabled = false);
        checkBtn.forEach(check => check.checked = false);
        venteCours.checked = true;
        enchereOuverte.disabled = true;
        mesEncheres.disabled = true;
        enchereRemportees.disabled = true;
    }
});