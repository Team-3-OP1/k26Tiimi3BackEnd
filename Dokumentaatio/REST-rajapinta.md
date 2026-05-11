# REST-rajapinnan dokumentaatio

## Yleiskuvaus

Tämä dokumentti kuvaa sovelluksen toteutetut REST-endpointit. Rajapinta on luku-API, eli tällä hetkellä käytössä ovat vain `GET`-pyynnöt.

Rajapinnan tarkoitus on tarjota frontendille ja ulkopuolisille testaajille pääsy tuotteisiin ja valmistajiin liittyvään dataan JSON-muodossa.

## Perusosoite

Paikallisesti ajettaessa Spring Boot käyttää oletuksena porttia `8080`, joten esimerkkiperusosoite on:

```text
http://localhost:8080
```

Esimerkiksi:

```text
http://localhost:8080/api/tuotteet
```

## Autentikointi ja käyttöoikeudet

- Kaikki `GET /api/**` endpointit ovat käytettävissä ilman kirjautumista.
- REST-kutsut palauttavat datan JSON-muodossa.
- Rajapintaan on määritelty CORS-sallinta frontend-osoitteelle `https://frontendtiimi3-opt3frontend.2.rahtiapp.fi/`.

## Tietomallit

### Vaate / tuote

Tuoteolio sisältää seuraavat kentät:

| Kenttä | Tyyppi | Kuvaus |
|---|---|---|
| `id` | number | Tuotteen yksilöllinen tunniste |
| `name` | string | Tuotteen nimi |
| `tyyppi` | object | Tuotteen tyyppi, esimerkiksi `vaate` tai `lelu` |
| `koko` | string or null | Tuotteen koko, mahdolliset arvot ovat `S`, `M`, `L` |
| `price` | number | Tuotteen hinta |
| `varastoMaara` | number | Varastossa oleva määrä |
| `valmistaja` | object | Tuotteen valmistaja |

Esimerkkituote:

```json
{
  "id": 1,
  "name": "T-paita",
  "tyyppi": {
    "id": 1,
    "nimi": "vaate"
  },
  "koko": "M",
  "price": 19.9,
  "varastoMaara": 12,
  "valmistaja": {
    "id": 2,
    "name": "Acme"
  }
}
```

### Valmistaja

Valmistajaolio sisältää seuraavat kentät:

| Kenttä | Tyyppi | Kuvaus |
|---|---|---|
| `id` | number | Valmistajan yksilöllinen tunniste |
| `name` | string | Valmistajan nimi |

Esimerkkivalmistaja:

```json
{
  "id": 2,
  "name": "Acme"
}
```

## Endpointit

### 1. Hae kaikki tuotteet

- **Metodi:** `GET`
- **Endpoint:** `/api/tuotteet`
- **Tarkoitus:** Palauttaa kaikki tuotteet listana.
- **Polkuparametrit:** Ei ole
- **Kyselyparametrit:** Ei ole
- **Autentikointi:** Ei vaadi kirjautumista

Esimerkkipyyntö:

```http
GET http://localhost:8080/api/tuotteet
```

Esimerkkivastaus:

```json
[
  {
    "id": 1,
    "name": "T-paita",
    "tyyppi": {
      "id": 1,
      "nimi": "vaate"
    },
    "koko": "M",
    "price": 19.9,
    "varastoMaara": 12,
    "valmistaja": {
      "id": 2,
      "name": "Acme"
    }
  },
  {
    "id": 2,
    "name": "Pehmolelu",
    "tyyppi": {
      "id": 2,
      "nimi": "lelu"
    },
    "koko": null,
    "price": 14.5,
    "varastoMaara": 4,
    "valmistaja": {
      "id": 3,
      "name": "ToyHouse"
    }
  }
]
```

### 2. Hae yksi tuote id:n perusteella

- **Metodi:** `GET`
- **Endpoint:** `/api/tuote/{id}`
- **Tarkoitus:** Palauttaa yhden tuotteen annetulla tunnisteella.
- **Polkuparametrit:** `id` = tuotteen tunniste
- **Kyselyparametrit:** Ei ole
- **Autentikointi:** Ei vaadi kirjautumista

Esimerkkipyyntö:

```http
GET http://localhost:8080/api/tuote/1
```

Esimerkkivastaus:

```json
{
  "id": 1,
  "name": "T-paita",
  "tyyppi": {
    "id": 1,
    "nimi": "vaate"
  },
  "koko": "M",
  "price": 19.9,
  "varastoMaara": 12,
  "valmistaja": {
    "id": 2,
    "name": "Acme"
  }
}
```

Huomio:

- Jos tuotetta ei löydy annetulla `id`:llä, toteutus palauttaa arvon `null`.

### 3. Hae kaikki vaatteet

- **Metodi:** `GET`
- **Endpoint:** `/api/vaatteet`
- **Tarkoitus:** Palauttaa vain ne tuotteet, joiden tyyppi on `vaate`.
- **Polkuparametrit:** Ei ole
- **Kyselyparametrit:** Ei ole
- **Autentikointi:** Ei vaadi kirjautumista

Esimerkkipyyntö:

```http
GET http://localhost:8080/api/vaatteet
```

Esimerkkivastaus:

```json
[
  {
    "id": 1,
    "name": "T-paita",
    "tyyppi": {
      "id": 1,
      "nimi": "vaate"
    },
    "koko": "M",
    "price": 19.9,
    "varastoMaara": 12,
    "valmistaja": {
      "id": 2,
      "name": "Acme"
    }
  }
]
```

### 4. Hae valmistajan tuotteet

- **Metodi:** `GET`
- **Endpoint:** `/api/valmistaja/{valmistajaId}/vaatteet`
- **Tarkoitus:** Palauttaa kaikki tuotteet, jotka kuuluvat tietylle valmistajalle.
- **Polkuparametrit:** `valmistajaId` = valmistajan tunniste
- **Kyselyparametrit:** Ei ole
- **Autentikointi:** Ei vaadi kirjautumista

Esimerkkipyyntö:

```http
GET http://localhost:8080/api/valmistaja/2/vaatteet
```

Esimerkkivastaus:

```json
[
  {
    "id": 1,
    "name": "T-paita",
    "tyyppi": {
      "id": 1,
      "nimi": "vaate"
    },
    "koko": "M",
    "price": 19.9,
    "varastoMaara": 12,
    "valmistaja": {
      "id": 2,
      "name": "Acme"
    }
  }
]
```

### 5. Hae kaikki valmistajat

- **Metodi:** `GET`
- **Endpoint:** `/api/valmistajat`
- **Tarkoitus:** Palauttaa kaikki valmistajat listana.
- **Polkuparametrit:** Ei ole
- **Kyselyparametrit:** Ei ole
- **Autentikointi:** Ei vaadi kirjautumista

Esimerkkipyyntö:

```http
GET http://localhost:8080/api/valmistajat
```

Esimerkkivastaus:

```json
[
  {
    "id": 2,
    "name": "Acme"
  },
  {
    "id": 3,
    "name": "ToyHouse"
  }
]
```

## HTTP-statuskoodit

Toteutuksen perusteella yleisimmät vastaukset ovat:

| Status | Merkitys |
|---|---|
| `200 OK` | Pyyntö onnistui |
| `500 Internal Server Error` | Palvelimella tapahtui virhe |

Huomio:

- Endpointissa `/api/tuote/{id}` puuttuvaa tuotetta ei käsitellä erillisellä `404 Not Found` -vastauksella, vaan vastausrunko voi olla `null`.

## Testaus Postmanilla

Rajapintaa voi testata Postmanissa näin:

1. Käynnistä backend-sovellus.
2. Luo Postmanissa uusi `GET`-pyyntö.
3. Syötä endpointin osoite, esimerkiksi `http://localhost:8080/api/tuotteet`.
4. Lähetä pyyntö painamalla `Send`.
5. Tarkista, että vastaus tulee JSON-muodossa.

Esimerkkitestit:

- `GET http://localhost:8080/api/tuotteet`
- `GET http://localhost:8080/api/tuote/1`
- `GET http://localhost:8080/api/vaatteet`
- `GET http://localhost:8080/api/valmistaja/2/vaatteet`
- `GET http://localhost:8080/api/valmistajat`

## Yhteenveto

Sovelluksen REST-rajapinta tarjoaa julkiset lukuoperaatiot tuotteille ja valmistajille. Dokumentaation avulla rajapintaa voidaan käyttää ja testata esimerkiksi Postmanilla ilman, että käyttöliittymää tarvitsee avata.
