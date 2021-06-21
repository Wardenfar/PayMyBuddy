describe("Login Tests", function () {

    it("Should login", function () {
        cy.visit("/login")
        cy.get("#email").type("admin@gmail.com")
        cy.get("#password").type("testtest")
        cy.get("form").submit()
        cy.url().should('contain', '/home')
    })
})