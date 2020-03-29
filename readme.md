# Petclinic extension

[![Build Status](https://travis-ci.com/gii-is-DP2/DP2-1920-GI-02.svg?branch=master)](https://travis-ci.com/gii-is-DP2/DP2-1920-GI-02)

This is a (manual) fork of https://github.com/gii-is-DP2/spring-petclinic to be used for the DP2 course. We will extend the project provided by the professors.

Our project consists in extending the project provided by the professors. The main features we are going to improve or implement are:
* The way visits are used: Instead of just having the owner or an administrator register the date of a visit, we will implement a fully automated scheduling system. An owner will be able to select a free timeslot with a veterinary of his choice, which will then be registered in the system.
* Payment information for a visit, including credit card data if applicable, will be stored in the system.
* In order to estimate the duration of a visit (for the scheduling) and its cost (for the payment system), we will categorize the visits by type (operation, revision, consultation)
* Veterinarians will be able to add a diagnosis to a visit, including prescriptions for medications.


## Team members

We are team 2 of the english language group:

* Claudia Guerruero Cuenca ([Clauuu](https://github.com/Clauuu))
* Miguel Macarro Klepsch ([macarro](https://github.com/macarro))
* José Manuel Volante González ([LetonTriste](https://github.com/LetonTriste))


## Running the application in Eclipse:

1) On the command line
```
git clone https://github.com/gii-is-DP2/DP2-1920-GI-02
```
2) Inside Eclipse
```
File -> Import -> Maven -> Existing Maven project
```
3) Build using the Eclipse launcher (right click on project and `Run As -> Maven install`) to generate the css.

4) Run the application main method by right clicking on it and choosing `Run As -> Java Application`.

