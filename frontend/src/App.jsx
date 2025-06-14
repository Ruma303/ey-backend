import { Container, Paper, Typography, Box, Button } from "@mui/material";

function App() {

  const serviceList = ["Lorem ipsum dolor sit, amet consectetur adipisicing elit. Consequuntur architecto doloribus aperiam sequi ipsa corrupti perferendis nulla in ullam rerum animi, cumque cum amet quis quaerat numquam id alias itaque!", "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Consequuntur architecto doloribus aperiam sequi ipsa corrupti perferendis nulla in ullam rerum animi, cumque cum amet quis quaerat numquam id alias itaque!", "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Consequuntur architecto doloribus aperiam sequi ipsa corrupti perferendis nulla in ullam rerum animi, cumque cum amet quis quaerat numquam id alias itaque!"]


  return (
    <Container sx={{ mt: 4, height: "100vh" }}>
      <Typography variant="h1"
        sx={{ my: 1, p: 1, color: "primary.main", ':hover': "secondary.main" }}
      >Hello World</Typography>
      <Typography sx={{ p: 1 }}>Hello World</Typography>
      <Box
        sx={{
          display: "flex",
          flexDirection: { xs: "column", md: "row" },
          alignItems: "center",
          justifyContent: "center",
          height: "100%",
        }}
      >
        {serviceList && serviceList.map((item, index) => (
          <Paper elevation={3} sx={{ width: { xs: 1, md: 320 } }}>
            <Box sx={{ mt: 3, p: 2 }}>
              <Typography key={index}
                sx={{  }}
              >{item}</Typography>
              <Button variant="contained" color="secondary" sx={{ mt: 2 }}>
                Press here</Button>
            </Box>
          </Paper>
        ))}
      </Box>
    </Container >
  )
}

export default App
