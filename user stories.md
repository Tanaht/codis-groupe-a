# Créer une intervention

## Description

En tant que opérateur Codis, je peux créer une nouvelle intervention afin de d'envoyer des moyens et des personnels sur place, au moyen de :

- Une adresse d’intervention

- Un code sinistre
	- secours à personne (SAP)
	- incendie (INC)

- Une liste de moyens (dite « premier départ »), composée d'un ou plusieurs véhicules dans la liste ci-après
	- VSAV (véhicule de secours aux victimes, symbole par défaut de couleur verte)
	- FPT (fourgon pompe-tonne, symbole par défaut de couleur rouge)
	- VLCG (VL chef de groupe, couleur par défaut rouge si intervention pour incendie, verte si intervention pour secours à personnes)


## Compléments


## Critères d'acceptance

Conformité aux spécifications ci-dessus

## Valeur métier

500

## Complexité

À estimer par l'équipe en sprint planning

# Se connecter à une intervention

## Description

En tant que _utilisateur SIT_ je veux pouvoir me connecter sur une tablette au moyen de :

- un nom de login

- un mot de passe

 afin de retrouver toutes les informations sur l'intervention qui me concerne.

## Compléments
NB : par souci de simplification on ne gérera pas de notions de droits, _ie_ n’importe qui peut se connecter.

## Critères d'acceptance

Conformité aux spécifications ci-dessus

## Valeur métier

500

## Complexité

À estimer par l'équipe en sprint planning

# Lire la carte

## Description

En tant que intervenant, je peux obtenir la carte des lieux de l'intervention afin de connaître tous les traits topographiques, les moyens de prévision SP.


## Compléments

Les données sont obtenues depuis le SIG opérationnel.


## Critères d'acceptance

Conformité aux spécifications ci-dessus, conformité à l'ordre graphique national des sapeurs-pompiers.

## Valeur métier

500

## Complexité

À estimer par l'équipe en sprint planning



# Confirmer la position d'un moyen

## Description

En tant que intervenant, je peux consulter la position prévue pour le ou les engins que je commande (CA ou CDG) et confirmer la mise en position effective du ou des moyens.


## Compléments

Les mises à jour faites sur une tablette sont propagées sur les tablettes connectées à l’intervention.
Le tableau des moyens est mis à jour automatiquement.


## Critères d'acceptance

Conformité aux spécifications ci-dessus

## Valeur métier

300

## Complexité

À estimer par l'équipe en sprint planning

# Mettre à jour les moyens dans l’ordre graphique

## Description

En tant que intervenant, je peux placer des symboles de moyens sur la carte tactique afin de représenter les actions en cours ou prévues, dans le respect de la charte graphique.


## Compléments

- Les mises à jour faites sur une tablette sont propagées sur les tablettes connectées à l’intervention.
- Le tableau des moyens est mis à jour automatiquement.
- L’ordre graphique respecte la charte graphique ENSOSP


## Critères d'acceptance

Conformité aux spécifications ci-dessus

## Valeur métier

300

## Complexité

À estimer par l'équipe en sprint planning


# Demander des moyens supplémentaires

## Description

En tant que COS, je peux ajouter des véhicules dans le tableau des moyens ou sur la SITAC afin que le Codis puisse valider, sélectionner et engager les moyens supplémentaires demandés


## Compléments


## Critères d'acceptance

Conformité aux spécifications ci-dessus

## Valeur métier

300

## Complexité

À estimer par l'équipe en sprint planning



# Planifier un trajet de drone par segments

## Description

En tant que _utilisateur SIT_ je veux pouvoir définir depuis une tablette le trajet d’un drone de surveillance afin qu’il suive un parcours prédéfini (suite de segments, fermée ou non)

## Compléments

Le drone est associé à une intervention, celle pour laquelle l’utilisateur est connecté au système.

## Critères d'acceptance

Conformité aux spécifications ci-dessus

## Valeur métier

500

## Complexité

À estimer par l'équipe en sprint planning

# Planifier un trajet de drone par zone

## Description

En tant que _utilisateur SIT_ je veux pouvoir définir depuis une tablette le trajet d’un drone de surveillance survole l’ensemble d’une zone déterminée par un contour extérieur fermé et une ou plusieurs zones d’exclusion dans ce contour.

## Compléments

Le drone est associé à une intervention, celle pour laquelle l’utilisateur est connecté au système.

## Critères d'acceptance

Conformité aux spécifications ci-dessus

## Valeur métier

500

## Complexité

À estimer par l'équipe en sprint planning

# Transmettre la vidéo drone en temps réel drone-\>tablettes

## Description

En tant que _utilisateur SIT_ je veux pouvoir recevoir la vidéo émise par un drone de surveillance depuis toute tablette connectée à l’intervention qui emploie ce drone.

## Compléments


## Critères d'acceptance

Conformité aux spécifications ci-dessus

## Valeur métier

500

## Complexité

À estimer par l'équipe en sprint planning

# Prendre et transmettre des photos drone-\>tablettes

## Description

En tant que _utilisateur SIT_ je veux pouvoir choisir des points d’intérêt sur la carte afin que le drone prenne une photo aérienne de chaque point d’intérêt et l’archive dans une collection de photos transmise à toute tablette connectée à l’intervention qui emploie ce drone.

## Compléments

Chaque photo de la collection est horodatée et géomarquée

## Critères d'acceptance

Conformité aux spécifications ci-dessus

## Valeur métier

500

## Complexité

À estimer par l'équipe en sprint planning

