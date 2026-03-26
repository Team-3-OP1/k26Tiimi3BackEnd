# gitHarkkaTiimi3
Ohjelmistoprojekti I GitHub-käytön harjoittelua. 

Tavoite: 

A. yritetään saada ns. konflikti aikaiseksi. Konfliktissa kaksi tai useampi henkilö on muuttanut samanaikaisesti samaa tiedostoa, ja git ei osaa valita mikä on oikea versio. 

B. saadaan konflikti tilanne korjattua. 

Tehtävän kuvaus
Ota tämä projekti itsellesi git clone komennolla (git clone https://github.com/h01340/gitHarkkaTiimi3.git)

A 
1. Päivitä omalla koneellasi tiedostoa "UpdateMe.txt"
2. Vie muutokset GitHubiin 
  2.1. Lisätään tiedostot ns. staging tilaan käyttämällä komentoa git add .
  2.2. Lisätään tiedostot lokaaliin repositoryyn käyttämällä komentoa git commit -m "laita lyhyt kuvaus muutoksesta" 
  2.3. Viedään muutokset palvelimen repositoryyn komennolla git push

B. 
Saitko konfliktin aikaiseksi? 
1. Jos kyllä, niin korjatkaa editorissa konfliktipaikka jättämällä sinne oikea teksti sekä poistamalla kulmasulut.
2.  Tämän jälkeen viekää päivitetty tiedosto uudelleen versionhallintaan. Tehkää se nyt seuraavien steppien mukaisesti: 
  2.1. Lisätään tiedostot ns. staging tilaan käyttämällä komentoa git add .
  2.2. Lisätään tiedostot lokaaliin repositoryyn käyttämällä komentoa git commit -m "laita lyhyt kuvaus muutoksesta" 
  2.3. TUODAAN PALVELIMELTA UUSIMMAT KOODIT komennolla git pull 
  2.4. Viedään heti tämän jälkeen muutokset lokaalista reposta palvelimen repositoryyn komennolla git push
