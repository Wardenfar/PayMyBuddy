// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })


Cypress.Commands.add('accessDenied', (url, denied) => {
    cy.request({
        url: url,
        failOnStatusCode: false,
        followRedirect: false
    }).then(res => {
        if (denied) {
            expect(res.status).to.eq(302)
        } else {
            expect(res.status).to.eq(200)
        }
    })
})

Cypress.Commands.add('failRegister', (email, password, confirm, message) => {
    cy.get('#email').type(email)
    cy.get('#firstName').type("First")
    cy.get('#lastName').type("LAST")
    cy.get("#password").type(password)
    cy.get("#confirm").type(confirm)
    cy.get("form").submit()

    cy.url().should('include', '/register')
    cy.contains('.alert-danger', message)
})

Cypress.Commands.add('successRegister', (email, firstName, lastName, password, confirm) => {
    cy.get('#email').type(email)
    cy.get('#firstName').type(firstName)
    cy.get('#lastName').type(lastName)
    cy.get("#password").type(password)
    cy.get("#confirm").type(confirm)
    cy.get("form").submit()

    cy.url().should('include', '/login')
    cy.contains('.alert-success', 'Success')
})

Cypress.Commands.add('login', (email, password) => {
    cy.get("#email").type(email)
    cy.get("#password").type(password)
    cy.get("form").submit()
    cy.url().should('include', '/home')
})