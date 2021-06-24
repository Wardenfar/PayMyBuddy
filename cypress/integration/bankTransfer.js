describe('Bank Transfer Tests', function () {

    beforeEach(function () {
        cy.request("/reset")

        cy.visit('/')
        cy.login('panda@gmail.com', 'testtest')
        cy.contains('.nav-item', 'Profile').click()
        cy.url().should('include', '/profile')
    })

    it('From Bank', function () {
        cy.contains('Transfer FROM')
        cy.get('#from').should('exist')

        cy.contains('.nav-link', '0.00 €')

        cy.get('#from #amount').clear().type("-10.25")
        cy.contains('#from button', 'Transfer').click()
        cy.contains('.nav-link', '0.00 €')
        cy.contains('.alert-danger', 'Amount must be positive')

        cy.get('#from #amount').clear().type("10.25")
        cy.contains('#from button', 'Transfer').click()
        cy.contains('.nav-link', '10.25 €')
        cy.contains('.alert-success', 'Success')
        cy.get('.alert-danger').should('not.exist')
        cy.contains('table', '10.25 €')
        cy.get('table tbody tr').should('have.length', 1)

        cy.get('#from #amount').clear().type("40.3")
        cy.contains('#from button', 'Transfer').click()
        cy.contains('.nav-link', '50.55 €')
        cy.contains('.alert-success', 'Success')
        cy.get('.alert-danger').should('not.exist')
        cy.contains('table', '40.30 €')
        cy.get('table tbody tr').should('have.length', 2)
    })

    it('To Bank', function () {
        cy.contains('Transfer TO')
        cy.get('#to').should('exist')

        cy.contains('.nav-link', '0.00 €')

        cy.get('#to #amount').clear().type("-10.25")
        cy.contains('#to button', 'Transfer').click()
        cy.contains('.nav-link', '0.00 €')
        cy.contains('.alert-danger', 'Amount must be positive')
        cy.get('table tbody tr').should('have.length', 0)

        cy.get('#from #amount').clear().type("50.00")
        cy.contains('#from button', 'Transfer').click()
        cy.contains('.nav-link', '50.00 €')
        cy.contains('.alert-success', 'Success')
        cy.get('.alert-danger').should('not.exist')
        cy.contains('table', '50.00 €')
        cy.get('table tbody tr').should('have.length', 1)

        cy.get('#to #amount').clear().type("10.50")
        cy.contains('#to button', 'Transfer').click()
        cy.contains('.nav-link', '39.50 €')
        cy.contains('.alert-success', 'Success')
        cy.get('.alert-danger').should('not.exist')
        cy.contains('table', '10.50 €')
        cy.get('table tbody tr').should('have.length', 2)
    })

})