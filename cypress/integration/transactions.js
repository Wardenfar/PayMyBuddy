describe('Bank Transfer Tests', function () {

    before(function () {
        cy.request("/reset")

        cy.visit('/')
        cy.login('panda@gmail.com', 'testtest')
    })

    it('Setup', function () {

        cy.contains('.nav-item', 'Profile').click()
        cy.url().should('include', '/profile')

        cy.get('#from #amount').clear().type("50.50")
        cy.contains('#from button', 'Transfer').click()
        cy.contains('.nav-link', '50.50 €')
        cy.contains('.alert-success', 'Success')

        cy.contains('.nav-item', 'Home').click()
        cy.url().should('include', '/home')
        cy.contains('#money', '50.50 €')

        cy.contains('.nav-link', 'Connections').click()
        cy.url().should('include', '/connections');
        cy.get('#email').clear().type("girafe@gmail.com")
        cy.contains('button', 'Add Connection').click()
        cy.get('.alert-danger').should('not.exist')
        cy.contains('.alert-success', 'Success')
    })

    it('Fail to transfer', function () {
        cy.visit('/')
        cy.login('panda@gmail.com', 'testtest')
        cy.contains('.nav-item', 'Transfer').click()
        cy.url().should('include', '/transfer')

        cy.get("#connectionList").select("-1")
        cy.get("#amount").clear().type("10.00")
        cy.get('#amount-tax').invoke('val').should('equal','10.50')
        cy.contains("form button", "Pay").click()
        cy.get(".alert-danger").should('exist')
            .and('contain', 'Error connection not found')

        cy.get("#connectionList").select('Girafe GIRAFE')
        cy.get("#amount")
            .invoke('removeAttr', 'min')
            .invoke('removeAttr', 'max')
            .clear().type("450.63")
        cy.get('#amount-tax').invoke('val').should('equal','473.16')
        cy.contains("form button", "Pay").click()
        cy.get(".alert-danger").should('exist')
            .and('contain', 'You don\'t have enough money')

        cy.get("#connectionList").select('Girafe GIRAFE')
        cy.get("#amount")
            .invoke('removeAttr', 'min')
            .invoke('removeAttr', 'max')
            .clear().type("-25.56")
        cy.contains("form button", "Pay").click()
        cy.get(".alert-danger").should('exist')
            .and('contain', 'The amount must be positive and non zero')

        cy.get("#connectionList").select('Girafe GIRAFE')
        cy.get("#amount")
            .invoke('removeAttr', 'min')
            .invoke('removeAttr', 'max')
            .clear().type("0.02")
        cy.contains("form button", "Pay").click()
        cy.get(".alert-danger").should('exist')
            .and('contain', 'Amount to small (minimum 0.2)')

        cy.get("table tbody tr").should('have.length', 0)
    })

    it('Should success transfer', function () {
        cy.visit('/')
        cy.login('panda@gmail.com', 'testtest')
        cy.contains('.nav-item', 'Transfer').click()
        cy.url().should('include', '/transfer')

        cy.get("#connectionList").select("Girafe GIRAFE")
        cy.get("#amount").clear().type("10.00")
        cy.get('#amount-tax').invoke('val').should('equal','10.50')
        cy.contains("form button", "Pay").click()
        cy.get(".alert-success").should('exist')

        cy.get("table tbody tr").should('have.length', 1)
        cy.get("table tbody tr").first()
            .should('contain', '10.00 €')

        cy.contains('.nav-item', 'Home').click()
        cy.url().should('include', '/home')
        cy.contains('#money', '40.00 €')
    })
})