import React from 'react';
import {render, screen} from '@testing-library/react';
import LearnReact from '../src/pages/learn_react/LearnReact';

test('renders learn react link', () => {
    render(<LearnReact/>);
    const linkElement = screen.getByText(/learn react/i);
    expect(linkElement).toBeInTheDocument();
});
