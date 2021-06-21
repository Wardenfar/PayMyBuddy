describe("Login Tests", function () {

    beforeEach(function () {
        cy.request("/reset")
    })

    it("Should login", function () {
        cy.visit("/login")
        cy.get("#email").type("panda@gmail.com")
        cy.get("#password").type("testtest")
        cy.get("form").submit()
        cy.url().should('include', '/home')
        cy.contains('Log Off').click()
        cy.url().should('include', '/login')
    })

    it("Should Failed login", function () {
        cy.visit("/login")
        cy.get("#email").type("fake@gmail.com")
        cy.get("#password").type("testtest")
        cy.get("form").submit()
        cy.url().should('include', '/login')
        cy.contains('Wrong Password')

        cy.visit("/login")
        cy.get("#email").type("panda@gmail.com")
        cy.get("#password").type("fake password")
        cy.get("form").submit()
        cy.url().should('include', '/login')
        cy.contains('Wrong Password')
    })
})