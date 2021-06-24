describe("Register Tests", function () {

    beforeEach(function () {
        cy.request("/reset")
    })

    it("Should fail register", function () {
        cy.visit("/")
        cy.contains("Register now").click()
        cy.url().should('include', '/register')

        cy.failRegister("panda@gmail.com", "testtest", "testtest", 'Email already exists')
        cy.failRegister("new@gmail.com", "testtest", "testtest2", 'The two passwords doesn\'t match')
        cy.failRegister("n@ew@gmail.com", "testtest", "testtest", 'Invalid email')
    })

    it("Should success register", function () {
        cy.visit("/")
        cy.contains("Register now").click()
        cy.url().should('include', '/register')

        cy.successRegister("user@gmail.com", 'Théo', 'EMERIAU', "testtest", "testtest")
        cy.login('user@gmail.com', 'testtest')
        cy.contains('0.00 €')
        cy.contains('Log Off').click()
        cy.url().should('include', '/login')
    })
})