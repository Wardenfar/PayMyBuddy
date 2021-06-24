describe('Test connections between users', function () {

    before(function () {
        cy.request('/reset')
    })

    it('Add connection', function () {
        cy.visit('/')
        cy.login('panda@gmail.com', 'testtest')
        cy.contains('.nav-link', 'Connections').click()
        cy.url().should('include', '/connections')

        cy.get('table tbody tr').should('have.length', 0)

        cy.get('#email').clear().type("fake@gmail.com")
        cy.contains('button', 'Add Connection').click()
        cy.contains('.alert-danger', 'No user found with this email')
        cy.get('table tbody tr').should('have.length', 0)

        cy.get('#email').clear().type("panda@gmail.com")
        cy.contains('button', 'Add Connection').click()
        cy.contains('.alert-danger', 'It\'s you !')
        cy.get('table tbody tr').should('have.length', 0)

        cy.get('#email').clear().type("girafe@gmail.com")
        cy.contains('button', 'Add Connection').click()
        cy.get('.alert-danger').should('not.exist')
        cy.contains('.alert-success', 'Success')
        cy.get('table tbody tr').should('have.length', 1)
        cy.get('table tbody')
            .should('contain', "girafe@gmail.com")
            .and('contain', 'Girafe')
            .and('contain', 'GIRAFE')

        cy.get('#email').clear().type("girafe@gmail.com")
        cy.contains('button', 'Add Connection').click()
        cy.contains('.alert-danger', 'The connection already exists')
        cy.get('table tbody tr').should('have.length', 1)
    })

    it('Remove a connection', function () {
        cy.visit('/')
        cy.login('panda@gmail.com', 'testtest')
        cy.contains('.nav-link', 'Connections').click()
        cy.url().should('include', '/connections')
        // Start with the test before
        cy.get('table tbody tr').should('have.length', 1)
        cy.get('table tbody')
            .should('contain', "girafe@gmail.com")
            .and('contain', 'Girafe')
            .and('contain', 'GIRAFE')

        cy.contains('button', 'Remove').click()
        cy.get('.alert-danger').should('not.exist')
        cy.contains('.alert-success', 'Success')

        cy.get('table tbody tr').should('have.length', 0)
    })

})