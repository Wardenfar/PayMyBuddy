describe('Bank Transfer Tests', function () {

    beforeEach(function () {
        cy.request("/reset")
    })

    it('From Bank', function () {
        cy.visit('/')
        cy.login('panda@gmail.com', 'testtest')
        cy.contains('.nav-item', 'Profile').click()
        cy.url().should('include', '/profile')
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

        cy.get('#from #amount').clear().type("40.3")
        cy.contains('#from button', 'Transfer').click()
        cy.contains('.nav-link', '50.55 €')
        cy.contains('.alert-success', 'Success')
        cy.get('.alert-danger').should('not.exist')
    })

    // it('To Bank', function () {
    //
    // })

})