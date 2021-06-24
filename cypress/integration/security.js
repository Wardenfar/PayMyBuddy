describe("Security Tests", function () {

    it("Should Allow", function () {
        cy.visit("/")
        cy.accessDenied("/login", false)
        cy.accessDenied("/register", false)
    })

    it("Should Deny", function () {
        cy.visit("/")
        cy.accessDenied("/home", true)
        cy.accessDenied("/transfer", true)
        cy.accessDenied("/fake", true)
    })
})