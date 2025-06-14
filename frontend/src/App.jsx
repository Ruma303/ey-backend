import { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Box,
  CircularProgress,
  Alert,
  List,
  ListItem,
  ListItemText,
  Paper,
  Grid,
} from '@mui/material';
import { createTheme, ThemeProvider, responsiveFontSizes } from '@mui/material/styles';

// Create a custom Material UI theme for better aesthetics
let theme = createTheme({
  typography: {
    fontFamily: '"Inter", sans-serif',
    h4: {
      fontWeight: 600,
      marginBottom: '1rem',
    },
    h6: {
      fontWeight: 500,
    },
  },
  palette: {
    primary: {
      main: '#2196f3', // Blue
    },
    secondary: {
      main: '#ff4081', // Pink
    },
    background: {
      default: '#f0f2f5', // Light grey for background
    },
  },
  components: {
    MuiPaper: {
      styleOverrides: {
        root: {
          borderRadius: '12px', // Rounded corners for Paper components
          padding: '20px',
          boxShadow: '0 4px 20px rgba(0, 0, 0, 0.05)', // Subtle shadow
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: '8px', // Rounded buttons
        },
      },
    },
  },
});

theme = responsiveFontSizes(theme); // Make font sizes responsive

function App() {
  const [persone, setPersone] = useState([]); // State to store fetched persons
  const [loading, setLoading] = useState(true); // State to manage loading status
  const [error, setError] = useState(null); // State to manage error messages

  // useEffect hook to fetch data when the component mounts
  useEffect(() => {
    // Start loading
    setLoading(true);
    setError(null);

    // Fetch data from the proxied backend endpoint
    fetch('/api/persone')
      .then(response => {
        if (!response.ok) {
          // If response is not OK (e.g., 404, 500), throw an error
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {
        // Set the fetched data to the 'persone' state
        setPersone(data);
      })
      .catch(error => {
        // Catch any errors during the fetch operation
        console.error('Error fetching persone:', error);
        setError('Impossibile caricare i dati delle persone. Riprova più tardi.'); // Set error message
      })
      .finally(() => {
        // Always set loading to false after fetch operation completes
        setLoading(false);
      });
  }, []); // Empty dependency array ensures this runs only once on mount

  return (
    <ThemeProvider theme={theme}>
      <Container sx={{ mt: 4, mb: 4, minHeight: '100vh', backgroundColor: 'background.default', borderRadius: '12px' }}>
        <Box sx={{ p: 3 }}>
          <Typography variant="h4" color="primary" align="center" sx={{ mb: 4 }}>
            Lista delle Persone
          </Typography>

          {loading && (
            // Show a loading indicator while fetching data
            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}>
              <CircularProgress />
              <Typography variant="h6" sx={{ ml: 2 }}>Caricamento persone...</Typography>
            </Box>
          )}

          {error && (
            // Show an error message if there was an error fetching data
            <Box sx={{ mt: 3 }}>
              <Alert severity="error">{error}</Alert>
            </Box>
          )}

          {!loading && !error && persone.length === 0 && (
            // Show a message if no persons are found
            <Box sx={{ mt: 3, p: 3, display: 'flex', justifyContent: 'center' }}>
              <Typography variant="h6" color="text.secondary">Nessuna persona trovata nel database.</Typography>
            </Box>
          )}

          {!loading && !error && persone.length > 0 && (
            // Display the list of persons using Material UI Grid and Paper for layout
            <Grid container spacing={4}>
              {persone.map((persona) => (
                <Grid item xs={12} sm={6} md={4} key={persona.id}>
                  <Paper elevation={3} sx={{ p: 3, display: 'flex', flexDirection: 'column', gap: 1 }}>
                    <Typography variant="h6" color="primary">
                      {persona.nome} {persona.cognome}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      **ID:** {persona.id}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      **Codice Fiscale:** {persona.codiceFiscale}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      **Data di Nascita:** {persona.dataNascita}
                    </Typography>

                    {persona.residenze && persona.residenze.length > 0 && (
                      <Box sx={{ mt: 2 }}>
                        <Typography variant="subtitle1" sx={{ mb: 1, fontWeight: 'bold' }}>
                          Residenze:
                        </Typography>
                        <List dense>
                          {persona.residenze.map((residenza) => (
                            <Paper elevation={1} sx={{ p: 1.5, mb: 1, borderRadius: '8px' }} key={residenza.id}>
                              <ListItem disablePadding>
                                <ListItemText
                                  primary={`${residenza.indirizzo}, ${residenza.cap} ${residenza.citta}`}
                                  secondary={`ID: ${residenza.id}`}
                                  primaryTypographyProps={{ variant: 'body2', fontWeight: 500 }}
                                  secondaryTypographyProps={{ variant: 'caption', color: 'text.secondary' }}
                                />
                              </ListItem>
                            </Paper>
                          ))}
                        </List>
                      </Box>
                    )}
                    {!persona.residenze || persona.residenze.length === 0 && (
                      <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                        Nessuna residenza associata.
                      </Typography>
                    )}
                  </Paper>
                </Grid>
              ))}
            </Grid>
          )}
        </Box>
      </Container>
    </ThemeProvider>
  );
}

export default App;
