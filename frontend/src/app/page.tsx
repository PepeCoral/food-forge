import { Login } from "@/components/auth/login";
import { SignUp } from "@/components/auth/signup";

export default function Home() {
  return (
    <main className="flex justify-center items-center w-full h-screen bg-primary">
      <Login></Login>
      <SignUp></SignUp>
    </main>

  );
}
