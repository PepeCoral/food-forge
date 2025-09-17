"use client";


import { Button } from "@/components/ui/button"
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { LoginRequest, AuthControllerApi, Configuration, RecipeControllerApi } from "@/lib/api";




async function handleLogin(event: React.FormEvent<HTMLFormElement>) {
  event.preventDefault()

  const form = event.currentTarget;
  const username = (form.elements.namedItem("username") as HTMLInputElement).value;
  const password = (form.elements.namedItem("password") as HTMLInputElement).value;


  const loginRequest: LoginRequest = { "username": username, "password": password }

  const configuration = new Configuration({ baseOptions: { withCredentials: true } })
  const apiInstance = new AuthControllerApi(configuration)



  console.log(loginRequest)

  try {
    await apiInstance.login(loginRequest)
  } catch (error) {
    console.error("Login failed:", error);
  }


}

export function Login() {
  return (
    <Card className="w-full max-w-sm">
      <CardHeader>
        <CardTitle>Login to your account</CardTitle>
        <CardDescription>
          Enter your username and password below to login to your account
        </CardDescription>
        <CardAction>
          <Button variant="link">Sign Up</Button>
        </CardAction>
      </CardHeader>
      <form onSubmit={handleLogin}>
        <CardContent>
          <div className="flex flex-col gap-6">
            <div className="grid gap-2">
              <Label htmlFor="email">Username</Label>
              <Input
                id="username"
                name="username"
                type="text"
                placeholder="cool_user"
                required
              />
            </div>
            <div className="grid gap-2">
              <div className="flex items-center">
                <Label htmlFor="password">Password</Label>
                <a
                  href="#"
                  className="ml-auto inline-block text-sm underline-offset-4 hover:underline"
                >
                  Forgot your password?
                </a>
              </div>
              <Input
                id="password"
                name="password"
                type="password"
                required />
            </div>
          </div>
        </CardContent>
        <CardFooter className="flex-col gap-2">
          <Button type="submit" className="w-full">
            Login
          </Button>

        </CardFooter>
      </form>
    </Card>
  )
}
